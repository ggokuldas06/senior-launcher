package com.example.senioroslauncher.assistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.resume

/**
 * Handles multilingual speech recognition, translation, and text-to-speech.
 * Updated to support manual language selection for accurate recognition.
 */
class MultilingualManager(private val context: Context) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var tts: TextToSpeech? = null
    private val languageIdentifier = LanguageIdentification.getClient()
    private val translators = mutableMapOf<String, Translator>()
    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val TAG = "MultilingualManager"

        // Map short codes to configuration
        // FIX: Replaced TranslateLanguage constants with string literals to avoid version errors
        val SUPPORTED_LANGUAGES = mapOf(
            "en" to LanguageConfig("English", "en-US", TranslateLanguage.ENGLISH),
            "hi" to LanguageConfig("हिन्दी (Hindi)", "hi-IN", TranslateLanguage.HINDI),
            "ta" to LanguageConfig("தமிழ் (Tamil)", "ta-IN", TranslateLanguage.TAMIL),
            "te" to LanguageConfig("తెలుగు (Telugu)", "te-IN", TranslateLanguage.TELUGU),
            "ml" to LanguageConfig("മലയാളം (Malayalam)", "ml-IN", "ml") // Changed from TranslateLanguage.MALAYALAM to "ml"
        )
    }

    data class LanguageConfig(
        val displayName: String,
        val locale: String, // Android Locale (e.g., "ta-IN")
        val mlkitCode: String // ML Kit Code (e.g., "ta")
    )

    data class SpeechResult(
        val text: String,
        val detectedLanguage: String,
        val translatedToEnglish: String,
        val confidence: Float
    )

    interface SpeechListener {
        fun onSpeechReady()
        fun onSpeechStart()
        fun onSpeechResult(result: SpeechResult)
        fun onSpeechError(error: String)
        fun onPartialResult(text: String)
    }

    fun initialize(onReady: () -> Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Default TTS to English initially
                tts?.language = Locale.US
                Log.d(TAG, "✓ TTS initialized")
                onReady()
            } else {
                Log.e(TAG, "TTS initialization failed")
            }
        }

        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            Log.d(TAG, "✓ Speech recognizer initialized")
        } else {
            Log.e(TAG, "Speech recognition not available")
        }
    }

    /**
     * UPDATED: Accepts [languageCode] (e.g., "ta", "hi") to configure the recognizer.
     * Defaults to system locale if not provided.
     */
    fun startListening(listener: SpeechListener, languageCode: String = "en") {
        if (speechRecognizer == null) {
            listener.onSpeechError("Speech recognizer not initialized")
            return
        }

        // Get the Android Locale string (e.g., "ta-IN") from our config map
        val targetLocale = SUPPORTED_LANGUAGES[languageCode]?.locale ?: Locale.getDefault().toLanguageTag()

        Log.d(TAG, "Start listening in: $targetLocale ($languageCode)")

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            // CRITICAL: Force the recognizer to listen in the selected language
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLocale)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, targetLocale)
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, targetLocale)

            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) { listener.onSpeechReady() }
            override fun onBeginningOfSpeech() { listener.onSpeechStart() }
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                val errorMsg = getErrorText(error)
                Log.e(TAG, "Speech error: $errorMsg")
                // Don't report "No match" as a fatal error, just a retry
                if (error != SpeechRecognizer.ERROR_NO_MATCH) {
                    listener.onSpeechError(errorMsg)
                }
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val confidences = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)

                if (!matches.isNullOrEmpty()) {
                    val text = matches[0]
                    val confidence = confidences?.getOrNull(0) ?: 1.0f
                    Log.d(TAG, "Raw Input ($languageCode): $text")

                    scope.launch {
                        // Pass the selected language code so we don't have to guess
                        processResult(text, confidence, languageCode, listener)
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    listener.onPartialResult(matches[0])
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        try {
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start listening", e)
            listener.onSpeechError(e.message ?: "Unknown error")
        }
    }

    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }

    /**
     * Process result using the KNOWN language code.
     */
    private suspend fun processResult(
        text: String,
        confidence: Float,
        languageCode: String,
        listener: SpeechListener
    ) {
        withContext(Dispatchers.IO) {
            // 1. If it's English, no translation needed
            if (languageCode == "en") {
                withContext(Dispatchers.Main) {
                    listener.onSpeechResult(SpeechResult(text, "en", text, confidence))
                }
                return@withContext
            }

            // 2. If it's Tamil/Hindi/etc, Translate to English
            Log.d(TAG, "Translating from $languageCode: $text")
            val translated = translateToEnglish(text, languageCode)

            val finalEnglish = translated ?: text // Fallback to original if translation fails
            Log.d(TAG, "Translation result: $finalEnglish")

            withContext(Dispatchers.Main) {
                listener.onSpeechResult(
                    SpeechResult(
                        text = text, // Original text (e.g. Tamil script)
                        detectedLanguage = languageCode,
                        translatedToEnglish = finalEnglish, // English text for Backend
                        confidence = confidence
                    )
                )
            }
        }
    }

    private suspend fun translateToEnglish(text: String, sourceLanguage: String): String? {
        val langConfig = SUPPORTED_LANGUAGES[sourceLanguage] ?: return null

        return suspendCancellableCoroutine { continuation ->
            val translatorKey = "${sourceLanguage}_en"

            // Get or create translator
            val translator = translators.getOrPut(translatorKey) {
                val options = TranslatorOptions.Builder()
                    .setSourceLanguage(langConfig.mlkitCode)
                    .setTargetLanguage(TranslateLanguage.ENGLISH)
                    .build()
                Translation.getClient(options)
            }

            // Check if model is downloaded
            val conditions = DownloadConditions.Builder().requireWifi().build()

            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    translator.translate(text)
                        .addOnSuccessListener { continuation.resume(it) }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Translation failed", e)
                            continuation.resume(null)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Model download failed", e)
                    continuation.resume(null)
                }
        }
    }

    fun speak(text: String, languageCode: String = "en") {
        val langConfig = SUPPORTED_LANGUAGES[languageCode]
        if (langConfig != null) {
            // Split locale string (e.g. "ta-IN" -> "ta", "IN")
            val parts = langConfig.locale.split("-")
            val locale = if (parts.size == 2) Locale(parts[0], parts[1]) else Locale(parts[0])
            tts?.language = locale
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping speech recognizer", e)
        }
    }

    fun cleanup() {
        speechRecognizer?.destroy()
        speechRecognizer = null
        tts?.shutdown()
        tts = null
        translators.values.forEach { it.close() }
        translators.clear()
    }
}