package com.example.senioroslauncher.ui.assistant

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.senioroslauncher.assistant.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

sealed class AssistantState {
    object Idle : AssistantState()
    data class Listening(val partialText: String? = null) : AssistantState()
    object Processing : AssistantState()
    data class Speaking(val message: String) : AssistantState()
    data class ConfirmationRequired(
        val message: String,
        val intent: String,
        val entities: EntityExtractor.ExtractedEntities
    ) : AssistantState()
    data class Error(val message: String) : AssistantState()
}

/**
 * Hybrid Voice Assistant ViewModel
 * Strategy:
 * 1. Primary: Cloud LLM (High intelligence, conversational)
 * 2. Fallback: Local TFLite (Offline capable, fast, privacy-focused)
 */
class VoiceAssistantViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<AssistantState>(AssistantState.Idle)
    val state: StateFlow<AssistantState> = _state.asStateFlow()

    // NEW: Language Selection State (Default English)
    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    // --- Core Components ---
    private val multilingualManager = MultilingualManager(application)
    private val actionExecutor = ActionExecutor(application)
    private val entityExtractor = EntityExtractor()

    // --- Intelligence Layers ---
    private val refinementService = IntentRefinementService() // Layer 1: Cloud
    private val localClassifier = IntentClassifier(application) // Layer 2: Edge

    private var pendingAction: PendingAction? = null
    private var lastMessage: String = ""

    // Critical intents that require user confirmation
    private val criticalIntents = setOf(
        "SOS_TRIGGER", "CALL_CONTACT", "MEDICATION_DELETE",
        "MEDICATION_LOG_SKIP", "APPOINTMENT_CANCEL",
        "SEND_MESSAGE", "CAREGIVER_CONTACT"
    )

    companion object {
        private const val TAG = "VoiceAssistantVM"
    }

    data class PendingAction(
        val intent: String,
        val entities: EntityExtractor.ExtractedEntities
    )

    init {
        initializeComponents()
    }

    private fun initializeComponents() {
        viewModelScope.launch {
            // A. Initialize Speech & Translation
            multilingualManager.initialize {
                Log.d(TAG, "✓ Multilingual manager initialized")
            }

            // B. Initialize Local TFLite (Backup)
            localClassifier.initialize()
                .onSuccess { Log.d(TAG, "✓ Local TFLite Backup Ready") }
                .onFailure { Log.e(TAG, "✗ Failed to load Local TFLite", it) }

            // C. Check Cloud Status
            val serverAlive = refinementService.checkServerConnection()
            Log.d(TAG, "Server Status at launch: ${if (serverAlive) "Online" else "Offline"}")
        }
    }

    // NEW: Function to set language from UI
    fun setLanguage(code: String) {
        _selectedLanguage.value = code
    }

    // UPDATED: Pass selected language to MultilingualManager
    fun startListening() {
        _state.value = AssistantState.Listening()

        multilingualManager.startListening(object : MultilingualManager.SpeechListener {
            override fun onSpeechReady() { Log.d(TAG, "Speech ready") }
            override fun onSpeechStart() { _state.value = AssistantState.Listening() }

            override fun onSpeechResult(result: MultilingualManager.SpeechResult) {
                Log.d(TAG, "Speech result detected: ${result.detectedLanguage}, translated: ${result.translatedToEnglish}")
                // Pass the DETECTED language (which matches selected) to the processor
                processCommandHybrid(result.translatedToEnglish, result.detectedLanguage)
            }

            override fun onSpeechError(error: String) {
                Log.e(TAG, "Speech error: $error")
                _state.value = AssistantState.Error(error)
                resetToIdleAfterDelay()
            }

            override fun onPartialResult(text: String) {
                _state.value = AssistantState.Listening(text)
            }
        }, _selectedLanguage.value) // <--- Pass the language code here
    }

    fun stopListening() {
        multilingualManager.stopListening()
        _state.value = AssistantState.Idle
    }

    /**
     * HYBRID LOGIC ENGINE
     */
    private fun processCommandHybrid(text: String, detectedLanguage: String) {
        viewModelScope.launch {
            _state.value = AssistantState.Processing
            val startTime = System.currentTimeMillis()
            var cloudSuccess = false

            // --- STEP 1: Attempt Cloud Inference ---
            try {
                Log.d(TAG, "☁️ Attempting Cloud Inference...")
                val llmResult = refinementService.refineIntent(text)

                if (llmResult.success && llmResult.intent != null) {
                    Log.d(TAG, "✅ Cloud Success: ${llmResult.intent} in ${System.currentTimeMillis() - startTime}ms")
                    cloudSuccess = true
                    handleLLMResult(llmResult, text, detectedLanguage)
                    return@launch
                }
            } catch (e: Exception) {
                Log.w(TAG, "⚠️ Cloud Exception (${e.message}), switching to fallback.")
            }

            // --- STEP 2: Fallback to Local TFLite ---
            if (!cloudSuccess) {
                Log.d(TAG, "🏠 Attempting Local Inference...")
                val localResult = localClassifier.classify(text)

                if (localResult.isAboveThreshold && localResult.intent != "UNKNOWN") {
                    Log.d(TAG, "✅ Local Success: ${localResult.intent} (${localResult.confidence})")
                    val entities = entityExtractor.extract(text, localResult.intent)
                    val localReply = generateLocalReply(localResult.intent, entities)

                    // Proceed to execution
                    handleExecution(localResult.intent, entities, localReply, detectedLanguage)
                } else {
                    Log.e(TAG, "❌ Both Cloud and Local failed.")
                    speak("I'm sorry, I didn't understand that.", detectedLanguage)
                }
            }
        }
    }

    private fun handleLLMResult(
        result: IntentRefinementService.RefinementResult,
        originalText: String,
        language: String
    ) {
        val entities = convertLLMEntities(result.entities, originalText)
        handleExecution(result.intent!!, entities, result.reply, language)
    }

    /**
     * Unified logic to check safety before running the action.
     */
    private fun handleExecution(
        intent: String,
        entities: EntityExtractor.ExtractedEntities,
        replyMessage: String?,
        language: String
    ) {
        // 1. Check Critical Confirmation (Safety Check)
        if (intent in criticalIntents) {
            val message = replyMessage ?: "Are you sure you want to do this?"
            _state.value = AssistantState.ConfirmationRequired(message, intent, entities)
            pendingAction = PendingAction(intent, entities)
            return
        }

        // 2. If safe, execute directly
        executeAction(intent, entities, language, replyMessage)
    }

    /**
     * This performs the actual task execution via ActionExecutor.
     */
    private fun executeAction(
        intent: String,
        entities: EntityExtractor.ExtractedEntities,
        language: String,
        preferredSuccessMessage: String? = null
    ) {
        actionExecutor.execute(intent, entities) { result ->
            if (result.requiresPermission != null) {
                speak("I need permission to do that. Please grant it on the screen.", language)
            } else if (result.requiresConfirmation) {
                // Secondary confirmation (e.g. from the action logic itself)
                _state.value = AssistantState.ConfirmationRequired(result.message, intent, entities)
                pendingAction = PendingAction(intent, entities)
            } else if (result.success) {
                // Use the LLM's polite reply if available, otherwise the default action message
                val finalMessage = if (!preferredSuccessMessage.isNullOrEmpty()) preferredSuccessMessage else result.message
                speak(finalMessage, language)
            } else {
                val message = result.message.ifEmpty { "Sorry, I couldn't do that" }
                speak(message, language)
            }
        }
    }

    /**
     * Called when user taps "Confirm" on the UI
     */
    fun confirmAction() {
        val action = pendingAction ?: return
        _state.value = AssistantState.Processing
        // Execute action, assuming English context for confirmation execution
        executeAction(action.intent, action.entities, "en")
        pendingAction = null
    }

    fun cancelAction() {
        pendingAction = null
        speak("Action cancelled", "en")
    }

    // --- Helpers ---

    private fun generateLocalReply(intent: String, entities: EntityExtractor.ExtractedEntities): String {
        return when(intent) {
            "SET_ALARM" -> "Setting alarm."
            "CALL_CONTACT" -> "Calling ${entities.contactName ?: "contact"}."
            else -> "Processing."
        }
    }

    private fun convertLLMEntities(llmEntities: Map<String, String?>, rawText: String): EntityExtractor.ExtractedEntities {
        var time: Calendar? = null
        val timeStr = llmEntities["time"] ?: llmEntities["alarmTime"]
        if (timeStr != null) {
            try { time = parseTimeString(timeStr) }
            catch (e: Exception) { time = EntityExtractor().extract(rawText, "SET_ALARM").time }
        }

        var duration: Int? = null
        llmEntities["duration"]?.let {
            try { duration = it.toIntOrNull() }
            catch (e: Exception) { duration = EntityExtractor().extract(rawText, "SET_TIMER").duration }
        }

        var number: Int? = null
        llmEntities["number"]?.let { try { number = it.toIntOrNull() } catch (e: Exception) {} }

        return EntityExtractor.ExtractedEntities(
            contactName = llmEntities["contactName"],
            phoneNumber = llmEntities["phoneNumber"],
            time = time,
            duration = duration,
            appName = llmEntities["appName"],
            medicationName = llmEntities["medicationName"],
            number = number,
            location = llmEntities["location"],
            destination = llmEntities["destination"],
            rawText = rawText
        )
    }

    private fun parseTimeString(timeStr: String): Calendar {
        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        val lower = timeStr.lowercase().trim()

        val pattern24 = Regex("(\\d{1,2}):(\\d{2})")
        val match24 = pattern24.find(lower)
        if (match24 != null) {
            val hour = match24.groupValues[1].toInt()
            val minute = match24.groupValues[2].toInt()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            if (calendar.before(now)) calendar.add(Calendar.DAY_OF_YEAR, 1)
            return calendar
        }

        // Fallback for AM/PM
        val patternAmPm = Regex("(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)", RegexOption.IGNORE_CASE)
        val matchAmPm = patternAmPm.find(lower)
        if (matchAmPm != null) {
            var hour = matchAmPm.groupValues[1].toInt()
            val minute = matchAmPm.groupValues[2].toIntOrNull() ?: 0
            val amPm = matchAmPm.groupValues[3].lowercase()
            if (amPm == "pm" && hour != 12) hour += 12
            if (amPm == "am" && hour == 12) hour = 0
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            if (calendar.before(now)) calendar.add(Calendar.DAY_OF_YEAR, 1)
            return calendar
        }
        throw IllegalArgumentException("Unsupported time format")
    }

    private fun speak(message: String, language: String) {
        lastMessage = message
        _state.value = AssistantState.Speaking(message)
        multilingualManager.speak(message, language)
        val speakDuration = (message.length * 50L).coerceAtLeast(2000L)
        viewModelScope.launch {
            kotlinx.coroutines.delay(speakDuration)
            _state.value = AssistantState.Idle
        }
    }

    private fun resetToIdleAfterDelay() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _state.value = AssistantState.Idle
        }
    }

    @Suppress("unused")
    fun repeatLast() {
        if (lastMessage.isNotEmpty()) {
            _state.value = AssistantState.Speaking(lastMessage)
            multilingualManager.speak(lastMessage, "en")
            viewModelScope.launch {
                kotlinx.coroutines.delay(lastMessage.length * 50L)
                _state.value = AssistantState.Idle
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        multilingualManager.cleanup()
        localClassifier.close()
    }
}