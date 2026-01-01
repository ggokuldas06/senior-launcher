package com.example.senioroslauncher.assistant

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.database.Cursor
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.RingtoneManager
import android.media.Ringtone
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.provider.AlarmClock
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.senioroslauncher.SeniorLauncherApp
import com.example.senioroslauncher.ui.calendar.CalendarActivity
import com.example.senioroslauncher.ui.emergency.EmergencyActivity
import com.example.senioroslauncher.ui.health.HealthActivity
import com.example.senioroslauncher.ui.medication.MedicationActivity
import com.example.senioroslauncher.ui.settings.SettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import android.provider.CallLog
import android.app.Notification
import androidx.core.app.NotificationManagerCompat
import com.example.senioroslauncher.data.database.entity.MedicationFrequency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import com.example.senioroslauncher.data.database.entity.MedicationEntity
import com.example.senioroslauncher.data.database.entity.MedicationScheduleEntity
import com.example.senioroslauncher.data.guardian.MedicationNotifier
import com.example.senioroslauncher.ui.ride.RideBookingActivity
import com.example.senioroslauncher.data.preferences.PreferencesManager // Ensure this exists
import kotlinx.coroutines.flow.first

/**
 * Execute actions based on classified intents
 * Handles all 60 intents from the model
 */
class ActionExecutor(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val TAG = "ActionExecutor"
    }

    data class ActionResult(
        val success: Boolean,
        val message: String,
        val requiresPermission: String? = null,
        val requiresConfirmation: Boolean = false
    )

    /**
     * Execute action for given intent and entities
     */
    fun execute(
        intent: String,
        entities: EntityExtractor.ExtractedEntities,
        onResult: (ActionResult) -> Unit
    ) {
        Log.d(TAG, "Executing: $intent")

        when (intent) {
            // === PHONE CONTROL INTENTS ===
            "OPEN_CAMERA" -> executeOpenCamera(onResult)
            "TAKE_SELFIE" -> executeTakeSelfie(onResult)
            "RECORD_VIDEO" -> executeRecordVideo(onResult)
            "OPEN_GALLERY" -> executeOpenGallery(onResult)
            "OPEN_APP" -> executeOpenApp(entities, onResult)
            "CALL_CONTACT" -> executeCallContact(entities, onResult)
            "SEND_MESSAGE" -> executeSendMessage(entities, onResult)
            "SET_ALARM" -> executeSetAlarm(entities, onResult)
            "SET_TIMER" -> executeSetTimer(entities, onResult)
            "FLASHLIGHT_ON" -> executeFlashlight(true, onResult)
            "FLASHLIGHT_OFF" -> executeFlashlight(false, onResult)
            "OPEN_SETTINGS" -> executeOpenSettings(onResult)
            "READ_NOTIFICATIONS" -> executeReadNotifications(onResult)
            "VOLUME_CONTROL" -> executeVolumeControl(entities, onResult)
            "CHECK_BATTERY" -> executeCheckBattery(onResult)
            "BOOK_RIDE" -> executeBookRide(entities, onResult)

            // === MEDICATION INTENTS ===
            "MEDICATION_LIST_TODAY" -> executeMedicationList(onResult)
            "MEDICATION_LOG_TAKEN" -> executeMedicationLogTaken(entities, onResult)
            "MEDICATION_LOG_SKIP" -> executeMedicationLogSkip(entities, onResult)
            "MEDICATION_ADD" -> executeMedicationAdd(entities, onResult)
            "MEDICATION_QUERY" -> executeMedicationQuery(entities, onResult)
            "MEDICATION_HISTORY" -> executeMedicationHistory(onResult)
            "MEDICATION_REMINDER_CHANGE" -> executeMedicationReminderChange(entities, onResult)
            "MEDICATION_DELETE" -> executeMedicationDelete(entities, onResult)
            "MEDICATION_INSTRUCTIONS" -> executeMedicationInstructions(entities, onResult)
            "MEDICATION_SIDE_EFFECTS" -> executeMedicationSideEffects(entities, onResult)
            "MEDICATION_INTERACTION" -> executeMedicationInteraction(entities, onResult)
            "MEDICATION_REFILL" -> executeMedicationRefill(entities, onResult)

            // === HEALTH & APPOINTMENT INTENTS ===
            "APPOINTMENT_CREATE" -> executeAppointmentCreate(entities, onResult)
            "APPOINTMENT_LIST" -> executeAppointmentList(onResult)
            "APPOINTMENT_CANCEL" -> executeAppointmentCancel(entities, onResult)
            "SOS_TRIGGER" -> executeSOS(onResult)
            "HEALTH_CHECKIN_START" -> executeHealthCheckin(onResult)
            "HEALTH_SUMMARY" -> executeHealthSummary(onResult)
            "HEALTH_RECORD" -> executeHealthRecord(entities, onResult)
            "CAREGIVER_CONTACT" -> executeCaregiverContact(entities, onResult)

            // === CONVENIENCE INTENTS ===
            "QUERY_TIME" -> executeQueryTime(onResult)
            "QUERY_DATE" -> executeQueryDate(onResult)
            "FIND_PHONE" -> executeFindPhone(onResult)
            "REPEAT_LAST" -> executeRepeatLast(onResult)
            "READ_SCREEN" -> executeReadScreen(onResult)
            "BRIGHTNESS_CONTROL" -> executeBrightnessControl(entities, onResult)
            "DO_NOT_DISTURB" -> executeDoNotDisturb(onResult)
            "SCREEN_LOCK" -> executeScreenLock(onResult)

            // === WEB KNOWLEDGE INTENTS ===
            "WEB_SEARCH" -> executeWebSearch(entities, onResult)
            "QA_FACT" -> executeQAFact(entities, onResult)
            "QA_WEATHER" -> executeQAWeather(onResult)
            "QA_NEWS" -> executeQANews(onResult)
            "QA_DEFINITION" -> executeQADefinition(entities, onResult)
            "QA_HEALTH_INFO" -> executeQAHealthInfo(entities, onResult)
            "SEARCH_LOCATION" -> executeSearchLocation(entities, onResult)
            "QA_CALCULATION" -> executeQACalculation(entities, onResult)
            "QA_CONVERSION" -> executeQAConversion(entities, onResult)
            "QA_MEDICINE_INFO" -> executeQAMedicineInfo(entities, onResult)

            // === SMALL TALK INTENTS ===
            "SMALL_TALK_GREET" -> executeSmallTalkGreet(onResult)
            "SMALL_TALK_THANKS" -> executeSmallTalkThanks(onResult)
            "SMALL_TALK_FEELINGS" -> executeSmallTalkFeelings(onResult)
            "SMALL_TALK_JOKE" -> executeSmallTalkJoke(onResult)
            "SMALL_TALK_ENCOURAGE" -> executeSmallTalkEncourage(onResult)
            "SMALL_TALK_GOODBYE" -> executeSmallTalkGoodbye(onResult)
            "SMALL_TALK_HELP" -> executeSmallTalkHelp(onResult)

            else -> onResult(ActionResult(false, "I don't know how to do that yet"))
        }
    }
    private fun executeBookRide(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val destination = entities.destination

        // If no destination specified, just open the app
        if (destination.isNullOrEmpty()) {
            val intent = Intent(context, RideBookingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening ride booking."))
            return
        }

        // Try to find saved address
        scope.launch(Dispatchers.IO) {
            val prefs = PreferencesManager(context)
            var address: String? = null
            var placeName = ""

            when (destination) {
                "home" -> {
                    address = prefs.homeAddress.first() // Using Flow.first()
                    placeName = "home"
                }
                "doctor" -> {
                    address = prefs.doctorAddress.first()
                    placeName = "the doctor"
                }
                "pharmacy" -> {
                    address = prefs.pharmacyAddress.first()
                    placeName = "the pharmacy"
                }
            }

            withContext(Dispatchers.Main) {
                if (!address.isNullOrEmpty()) {
                    // We have an address! Launch deep link directly.
                    openRideAppWithDestination(context, address)
                    onResult(ActionResult(true, "Booking a ride to $placeName."))
                } else if (placeName.isNotEmpty()) {
                    // Known place but no address saved
                    val intent = Intent(context, RideBookingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                    onResult(ActionResult(true, "Opening ride app. Please save your $placeName address first."))
                } else {
                    // Unknown destination (e.g. "Mall"), try to search or just open app
                    openRideAppWithDestination(context, destination) // Try passing raw query
                    onResult(ActionResult(true, "Looking for a ride to $destination."))
                }
            }
        }
    }

    /**
     * Helper: Reused logic from RideBookingActivity to launch Uber/Ola/Lyft
     */
    private fun openRideAppWithDestination(context: Context, address: String) {
        val encodedAddress = Uri.encode(address)

        // 1. Try Uber
        try {
            val uri = Uri.parse("uber://?action=setPickup&pickup=my_location&dropoff[formatted_address]=$encodedAddress")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.ubercab")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                return
            }
        } catch (e: Exception) { Log.e(TAG, "Uber failed", e) }

        // 2. Try Ola
        try {
            val uri = Uri.parse("olacabs://app/launch?landing_page=bk&drop_address=$encodedAddress")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.olacabs.customer")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                return
            }
        } catch (e: Exception) { Log.e(TAG, "Ola failed", e) }

        // 3. Try Lyft
        try {
            val uri = Uri.parse("lyft://ridetype?id=lyft&destination[address]=$encodedAddress")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("me.lyft.android")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                return
            }
        } catch (e: Exception) { Log.e(TAG, "Lyft failed", e) }

        // 4. Try Rapido
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("rapido://")).apply {
                setPackage("com.rapido.passenger")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                return
            }
        } catch (e: Exception) { Log.e(TAG, "Rapido failed", e) }

        // 5. Fallback: Open your RideBookingActivity
        val fallbackIntent = Intent(context, RideBookingActivity::class.java)
        fallbackIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(fallbackIntent)
    }    // ========== PHONE CONTROL ACTIONS ==========

    private fun executeOpenCamera(onResult: (ActionResult) -> Unit) {
        try {
            // Intent that just opens the camera app (No runtime permission needed)
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening camera"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open camera", e)
            onResult(ActionResult(false, "Couldn't open camera"))
        }
    }

    private fun executeTakeSelfie(onResult: (ActionResult) -> Unit) {
        try {
            // Same safe intent
            val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1)

            // 2. Extras for various specific manufacturers/versions
            intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
            intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
            intent.putExtra("front_camera", true)
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening camera for selfie"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open selfie camera", e)
            onResult(ActionResult(false, "Couldn't open selfie camera"))
        }
    }

    private fun executeRecordVideo(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening video camera"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open video camera", e)
            onResult(ActionResult(false, "Couldn't open video camera"))
        }
    }

    private fun executeOpenGallery(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening gallery"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open gallery", e)
            onResult(ActionResult(false, "Couldn't open gallery"))
        }
    }

    private fun executeOpenApp(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val appName = entities.appName
        if (appName.isNullOrEmpty()) {
            onResult(ActionResult(false, "Which app would you like to open?"))
            return
        }

        try {
            // 1. Try known hardcoded list first
            var packageName = getPackageName(appName)

            // 2. Fuzzy Search: Look through installed apps if not found above
            if (packageName == null) {
                val installedApps = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                val bestMatch = installedApps.find {
                    val label = context.packageManager.getApplicationLabel(it).toString()
                    label.contains(appName, ignoreCase = true)
                }
                packageName = bestMatch?.packageName
            }

            if (packageName != null) {
                val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                if (intent != null) {
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                    onResult(ActionResult(true, "Opening $appName"))
                } else {
                    onResult(ActionResult(false, "$appName is not installed"))
                }
            } else {
                onResult(ActionResult(false, "Couldn't find an app named $appName"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open app", e)
            onResult(ActionResult(false, "Couldn't open $appName"))
        }
    }

    private fun getPackageName(appName: String): String? {
        return when (appName.lowercase()) {
            "whatsapp" -> "com.whatsapp"
            "youtube" -> "com.google.android.youtube"
            "google maps", "maps" -> "com.google.android.apps.maps"
            "gmail" -> "com.google.android.gm"
            "facebook" -> "com.facebook.katana"
            "instagram" -> "com.instagram.android"
            "chrome" -> "com.android.chrome"
            "spotify" -> "com.spotify.music"
            "messenger" -> "com.facebook.orca"
            "twitter", "x" -> "com.twitter.android"
            "telegram" -> "org.telegram.messenger"
            "linkedin" -> "com.linkedin.android"
            "netflix" -> "com.netflix.mediaclient"
            else -> null
        }
    }

    private fun executeCallContact(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val contactName = entities.contactName
        if (contactName.isNullOrEmpty()) {
            onResult(ActionResult(false, "Who would you like to call?"))
            return
        }

        // 1. Try to find the number first
        val phoneNumber = getPhoneNumber(contactName)

        if (phoneNumber == null) {
            // If contact not found, ask user to check
            onResult(ActionResult(false, "I couldn't find a number for $contactName in your contacts."))
            return
        }

        // 2. Check Permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission missing? Open Dialer with number pre-filled (No permission needed for ACTION_DIAL)
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber") // <--- THIS PRE-FILLS THE NUMBER
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                onResult(ActionResult(true, "Opening dialer for $contactName"))
            } catch (e: Exception) {
                onResult(ActionResult(false, "Couldn't open dialer"))
            }
            return
        }

        // 3. Permission Granted - Open Dialer Pre-filled (User just presses call)
        // Note: You can use ACTION_CALL to call immediately, but ACTION_DIAL is safer/politer
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber") // <--- THIS PRE-FILLS THE NUMBER
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Calling $contactName...", requiresConfirmation = true))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to make call", e)
            onResult(ActionResult(false, "Couldn't make the call"))
        }
    }

    private fun executeSendMessage(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val contactName = entities.contactName
        if (contactName.isNullOrEmpty()) {
            onResult(ActionResult(false, "Who would you like to message?"))
            return
        }

        // 1. Get the phone number first (Reuse the helper function from the Call feature)
        val phoneNumber = getPhoneNumber(contactName)

        if (phoneNumber == null) {
            onResult(ActionResult(false, "I couldn't find a number for $contactName"))
            return
        }

        // 2. Extract the message body from rawText
        var messageBody = ""
        if (entities.rawText.isNotEmpty()) {
            // Updated regex to handle comma and other punctuation after the name
            // Matches: "text anish", "message anish,", "send message to anish:"
            val pattern = Regex("^(text|message|send message to|sms)\\s+${Regex.escape(contactName)}[,;:]?\\s*", RegexOption.IGNORE_CASE)
            messageBody = entities.rawText.replace(pattern, "").trim()

            // cleanup quotes if the user said "text anish 'hello'"
            messageBody = messageBody.removeSurrounding("\"", "\"").removeSurrounding("'", "'").trim()
        }

        try {
            // 3. Use ACTION_SENDTO to pre-fill number and body
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:$phoneNumber") // Sets the recipient

            if (messageBody.isNotEmpty()) {
                intent.putExtra("sms_body", messageBody) // Sets the message content
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

            // Give specific feedback based on what we did
            if (messageBody.isNotEmpty()) {
                onResult(ActionResult(true, "Drafting message to $contactName: \"$messageBody\"", requiresConfirmation = true))
            } else {
                onResult(ActionResult(true, "Opening messages for $contactName"))
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to send message", e)
            onResult(ActionResult(false, "Couldn't open messages app"))
        }
    }

    private fun executeSetAlarm(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val time = entities.time
        if (time == null) {
            onResult(ActionResult(false, "What time should I set the alarm for?"))
            return
        }

        try {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, time.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, time.get(Calendar.MINUTE))
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)

            val timeStr = SimpleDateFormat("h:mm a", Locale.getDefault()).format(time.time)

            // Check if it's tomorrow
            val now = Calendar.getInstance()
            val dayDiff = time.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR)
            val dayLabel = if (dayDiff > 0) " for tomorrow" else ""

            onResult(ActionResult(true, "Setting alarm for $timeStr$dayLabel"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to set alarm. Check Manifest permission.", e)
            onResult(ActionResult(false, "Couldn't set alarm"))
        }
    }

    private fun executeSetTimer(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val duration = entities.duration
        if (duration == null || duration <= 0) {
            onResult(ActionResult(false, "How long should the timer be?"))
            return
        }

        // FIX: Check raw text to see if user specified "seconds"
        // If "second" is found, use duration as is. Otherwise, default to minutes (multiply by 60).
        val isSeconds = entities.rawText.lowercase().contains("second")
        val lengthInSeconds = if (isSeconds) duration else duration * 60
        val unitLabel = if (isSeconds) "seconds" else "minutes"

        try {
            val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_LENGTH, lengthInSeconds) // Value is now correct (5 or 300)
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)

            // FIX: Update the success message to reflect the correct unit
            onResult(ActionResult(true, "Setting timer for $duration $unitLabel"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to set timer. Check Manifest permission.", e)
            onResult(ActionResult(false, "Couldn't set timer"))
        }
    }

    private fun executeFlashlight(on: Boolean, onResult: (ActionResult) -> Unit) {
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, on)
            onResult(ActionResult(true, if (on) "Flashlight turned on" else "Flashlight turned off"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to toggle flashlight", e)
            onResult(ActionResult(false, "Couldn't control flashlight"))
        }
    }

    private fun executeOpenSettings(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, SettingsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening settings"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open settings", e)
            onResult(ActionResult(false, "Couldn't open settings"))
        }
    }

    private fun executeReadNotifications(onResult: (ActionResult) -> Unit) {
        val messages = mutableListOf<String>()

        // 1. Check permissions
        val notificationListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        val isNotificationAccessGranted = notificationListeners?.contains(context.packageName) == true

        if (!isNotificationAccessGranted) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Please allow me to read notifications in the list."))
            return
        }

        // 2. Fetch Active Notifications
        val notifications = NotificationService.activeNotificationsList
        if (notifications != null) {
            for (sbn in notifications) {
                if (sbn.isClearable) {
                    val extras = sbn.notification.extras

                    // FIX: Use getCharSequence() instead of getString() to handle SpannableString
                    val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
                    val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
                    val appName = getAppNameFromPackage(sbn.packageName)

                    if (!title.isNullOrEmpty() && !text.isNullOrEmpty()) {
                        // Ignore generic "Checking for new messages"
                        if (text.contains("Checking for new messages", ignoreCase = true)) continue

                        // Ignore the notification if it is just the app name repeated
                        if (title == appName && text == appName) continue

                        messages.add("$appName says: $title, $text")
                    }
                }
            }
        }

        // 3. Fetch Missed Calls
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            val missedCalls = getMissedCalls()
            if (missedCalls.isNotEmpty()) {
                messages.add(0, "You have missed calls from: ${missedCalls.joinToString(", ")}")
            }
        }

        // 4. Final Result
        if (messages.isEmpty()) {
            onResult(ActionResult(true, "You have no new notifications."))
        } else {
            val summary = messages.distinct().joinToString(". ")
            onResult(ActionResult(true, summary))
        }
    }
    /**
     * Helper to get missed calls from CallLog
     */
    private fun getMissedCalls(): List<String> {
        val missedCalls = mutableListOf<String>()
        val projection = arrayOf(CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.IS_READ)

        // Filter: Type is MISSED and IS_READ is 0 (new)
        val selection = "${CallLog.Calls.TYPE} = ? AND ${CallLog.Calls.IS_READ} = 0"
        val selectionArgs = arrayOf(CallLog.Calls.MISSED_TYPE.toString())

        try {
            val cursor = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                "${CallLog.Calls.DATE} DESC"
            )

            cursor?.use {
                val nameIndex = it.getColumnIndex(CallLog.Calls.CACHED_NAME)
                val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)

                // Get only last 3 missed calls to avoid spamming
                var count = 0
                while (it.moveToNext() && count < 3) {
                    val name = it.getString(nameIndex)
                    val number = it.getString(numberIndex)
                    missedCalls.add(name ?: number ?: "Unknown")
                    count++
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to read call log", e)
        }
        return missedCalls.distinct()
    }

    /**
     * Helper to get human-readable app name
     */
    private fun getAppNameFromPackage(packageName: String): String {
        return try {
            val packageManager = context.packageManager
            val info = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(info).toString()
        } catch (e: Exception) {
            "App"
        }
    }
    private fun executeVolumeControl(
        entities: EntityExtractor.ExtractedEntities,
        onResult: (ActionResult) -> Unit
    ) {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

            val text = entities.rawText.lowercase()
            val step = maxVolume / 5 // roughly 20% increments for seniors

            when {
                text.contains("up") || text.contains("increase") || text.contains("louder") -> {
                    val newVolume = (currentVolume + step).coerceAtMost(maxVolume)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
                    val percent = (newVolume * 100 / maxVolume)
                    onResult(ActionResult(true, "Volume increased to $percent%"))
                }
                text.contains("down") || text.contains("decrease") || text.contains("lower") || text.contains("quieter") -> {
                    val newVolume = (currentVolume - step).coerceAtLeast(0)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
                    val percent = (newVolume * 100 / maxVolume)
                    onResult(ActionResult(true, "Volume decreased to $percent%"))
                }
                text.contains("mute") -> {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                    onResult(ActionResult(true, "Muted"))
                }
                text.contains("max") || text.contains("full") -> {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
                    onResult(ActionResult(true, "Volume set to 100%"))
                }
                else -> {
                    val percent = (currentVolume * 100 / maxVolume)
                    onResult(ActionResult(true, "Current volume is $percent%"))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to control volume", e)
            onResult(ActionResult(false, "Couldn't control volume"))
        }
    }


    private fun executeCheckBattery(onResult: (ActionResult) -> Unit) {
        try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            val isCharging = batteryManager.isCharging

            val message = if (isCharging) {
                "Battery is at $batteryLevel% and charging"
            } else {
                "Battery is at $batteryLevel%"
            }
            onResult(ActionResult(true, message))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to check battery", e)
            onResult(ActionResult(false, "Couldn't check battery"))
        }
    }

    // ========== MEDICATION ACTIONS ==========

    private fun executeMedicationList(onResult: (ActionResult) -> Unit) {
        // 1. Open the visual screen immediately
        try {
            val intent = Intent(context, MedicationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open medication screen", e)
        }

        // 2. Fetch data in background to read aloud
        scope.launch(Dispatchers.IO) {
            try {
                // Access the database via the Application context
                val app = context.applicationContext as SeniorLauncherApp
                val dao = app.database.medicationDao()

                // Get the current list (Flow.first() takes the immediate snapshot)
                val medications = dao.getAllActiveMedications().first()

                // 3. Format the speech string
                val speechText = if (medications.isEmpty()) {
                    "Opening your medication list. You don't have any saved medications yet."
                } else {
                    val listBuilder = StringBuilder("Here are your medications: ")

                    medications.forEachIndexed { index, med ->
                        // Make frequency sound natural
                        val freqString = when (med.frequency) {
                            MedicationFrequency.DAILY -> "daily"
                            MedicationFrequency.WEEKLY -> "weekly"
                            MedicationFrequency.MONTHLY -> "monthly"
                            MedicationFrequency.AS_NEEDED -> "as needed"
                        }

                        listBuilder.append("${med.name}, ${med.dosage}, taken $freqString. ")
                    }
                    listBuilder.toString()
                }

                // 4. Return result on Main thread
                withContext(Dispatchers.Main) {
                    onResult(ActionResult(true, speechText))
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching medications", e)
                withContext(Dispatchers.Main) {
                    onResult(ActionResult(true, "Opening your medication list."))
                }
            }
        }
    }

    private fun executeMedicationLogTaken(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val medName = entities.medicationName ?: "medication"
        onResult(ActionResult(true, "Marked $medName as taken"))
    }

    private fun executeMedicationLogSkip(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val medName = entities.medicationName ?: "dose"
        onResult(ActionResult(true, "Skipped $medName", requiresConfirmation = true))
    }

    private fun executeMedicationAdd(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        // --- 1. LOCAL REFINEMENT START ---
        var name = entities.medicationName
        var dosage = entities.dosage
        var frequency = entities.frequency ?: MedicationFrequency.DAILY
        val rawText = entities.rawText.lowercase()

        // A. If dosage is missing, try to find it in the raw text (IMPROVED REGEX)
        // Now catches "milligrams", "tablets", "pills", etc.
        if (dosage.isNullOrEmpty()) {
            val dosagePattern = Regex("(\\d+(\\.\\d+)?)\\s*(mg|milligrams?|g|grams?|ml|milliliters?|mcg|micrograms?|tablets?|pills?|capsules?|drops?)")
            val match = dosagePattern.find(rawText)
            if (match != null) {
                dosage = match.value
            }
        }

        // B. If frequency is missing, check raw text for common keywords
        if (entities.frequency == null) {
            if (rawText.contains("daily") || rawText.contains("every day") || rawText.contains("everyday")) frequency = MedicationFrequency.DAILY
            else if (rawText.contains("weekly") || rawText.contains("every week")) frequency = MedicationFrequency.WEEKLY
            else if (rawText.contains("monthly") || rawText.contains("every month")) frequency = MedicationFrequency.MONTHLY
            else if (rawText.contains("needed") || rawText.contains("pain")) frequency = MedicationFrequency.AS_NEEDED
        }

        // C. Clean the Name: Remove the dosage string and filler words like "reminder for"
        if (!name.isNullOrEmpty()) {
            // Remove dosage if present in name
            if (!dosage.isNullOrEmpty()) {
                name = name!!.replace(dosage!!, "", ignoreCase = true)
            }

            // Cleanup fillers
            name = name!!.replace("reminder", "", ignoreCase = true)
                .replace("added", "", ignoreCase = true)
                .replace(" for ", " ", ignoreCase = true) // Remove "for" inside text
                .replace(Regex("^for\\s+"), "") // Remove "for" at start
                .trim()
        }
        // --- LOCAL REFINEMENT END ---

        // CASE 1: Still missing info? Open form manually.
        if (name.isNullOrEmpty() || dosage.isNullOrEmpty()) {
            try {
                val intent = Intent(context, MedicationActivity::class.java)
                intent.putExtra("action", "add")
                // Pre-fill name if we have it
                if (!name.isNullOrEmpty()) intent.putExtra("medication_name", name)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

                val msg = if (name != null) "Opening form for $name. I need the dosage." else "Opening medication form."
                onResult(ActionResult(true, msg))
            } catch (e: Exception) {
                onResult(ActionResult(false, "Couldn't open medication form"))
            }
            return
        }

        // CASE 2: Have Name & Dosage -> Add directly to Database
        scope.launch(Dispatchers.IO) {
            try {
                val app = context.applicationContext as SeniorLauncherApp
                val medDao = app.database.medicationDao()
                val scheduleDao = app.database.medicationScheduleDao()

                // 1. Insert Medication
                val newMed = com.example.senioroslauncher.data.database.entity.MedicationEntity(
                    name = name!!,
                    dosage = dosage!!,
                    frequency = frequency,
                    notes = "Added via Voice Assistant"
                )
                val medId = medDao.insert(newMed)

                // 2. Insert Default Schedule (8:00 AM) unless it's "As Needed"
                if (frequency != MedicationFrequency.AS_NEEDED) {
                    val schedule = com.example.senioroslauncher.data.database.entity.MedicationScheduleEntity(
                        medicationId = medId,
                        hour = 8,
                        minute = 0
                    )
                    scheduleDao.insert(schedule)
                }

                // 3. Notify Guardians / Cloud
                val savedMed = medDao.getMedicationByIdSync(medId)
                if (savedMed != null) {
                    com.example.senioroslauncher.data.guardian.MedicationNotifier.notifyMedicationAdded(context, savedMed)
                }

                // 4. Success Response
                withContext(Dispatchers.Main) {
                    val freqString = when(frequency) {
                        MedicationFrequency.DAILY -> "daily"
                        MedicationFrequency.WEEKLY -> "weekly"
                        MedicationFrequency.MONTHLY -> "monthly"
                        MedicationFrequency.AS_NEEDED -> "as needed"
                    }
                    onResult(ActionResult(true, "Added $name, $dosage to your $freqString schedule."))
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to add medication via voice", e)
                withContext(Dispatchers.Main) {
                    onResult(ActionResult(false, "I couldn't save the medication. Please try using the app."))
                }
            }
        }
    }
    private fun executeMedicationQuery(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val targetMedName = entities.medicationName

        scope.launch(Dispatchers.IO) {
            try {
                val app = context.applicationContext as SeniorLauncherApp
                val medDao = app.database.medicationDao()
                val scheduleDao = app.database.medicationScheduleDao()

                // 1. Get relevant medications (Using .first() to read Flow)
                val allMeds = medDao.getAllActiveMedications().first()
                val medications = if (!targetMedName.isNullOrEmpty()) {
                    allMeds.filter { it.name.contains(targetMedName, ignoreCase = true) }
                } else {
                    allMeds
                }

                if (medications.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        val msg = if (targetMedName != null) "I couldn't find any medication named $targetMedName." else "You don't have any active medications."
                        onResult(ActionResult(true, msg))
                    }
                    return@launch
                }

                // 2. Find the next dose
                var nextDoseTime: Calendar? = null
                var nextDoseMed: String = ""
                val now = Calendar.getInstance()
                var bestDiff = Long.MAX_VALUE

                for (med in medications) {
                    // FIX: Use .first() to get the List from the Flow
                    val schedules = scheduleDao.getSchedulesForMedication(med.id).first()

                    for (schedule in schedules) {
                        val doseTime = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, schedule.hour)
                            set(Calendar.MINUTE, schedule.minute)
                            set(Calendar.SECOND, 0)
                        }

                        if (doseTime.before(now)) {
                            doseTime.add(Calendar.DAY_OF_YEAR, 1)
                        }

                        val diff = doseTime.timeInMillis - now.timeInMillis
                        if (diff < bestDiff) {
                            bestDiff = diff
                            nextDoseTime = doseTime
                            nextDoseMed = med.name
                        }
                    }
                }

                // 3. Construct Response
                val responseText = if (nextDoseTime != null) {
                    val timeStr = SimpleDateFormat("h:mm a", Locale.getDefault()).format(nextDoseTime.time)
                    val dayStr = if (nextDoseTime.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)) "tomorrow" else "today"

                    if (!targetMedName.isNullOrEmpty()) {
                        "Your next dose of $nextDoseMed is at $timeStr $dayStr."
                    } else {
                        "Your next medication is $nextDoseMed at $timeStr $dayStr."
                    }
                } else {
                    "That medication is set to match your needs, so there is no fixed time."
                }

                withContext(Dispatchers.Main) {
                    onResult(ActionResult(true, responseText))
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error querying medication", e)
                withContext(Dispatchers.Main) {
                    onResult(ActionResult(false, "I couldn't check your schedule right now."))
                }
            }
        }
    }

    private fun executeMedicationHistory(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, MedicationActivity::class.java)
            intent.putExtra("tab", "history")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening medication history"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't open history"))
        }
    }

    private fun executeMedicationReminderChange(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Opening medication settings"))
    }

    private fun executeMedicationDelete(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val medName = entities.medicationName ?: "this medication"
        onResult(ActionResult(true, "Are you sure you want to remove $medName?", requiresConfirmation = true))
    }

    private fun executeMedicationInstructions(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Let me show you the medication instructions"))
    }

    private fun executeMedicationSideEffects(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I'll look up the side effects for you"))
    }

    private fun executeMedicationInteraction(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Let me check if these medications are safe together"))
    }

    private fun executeMedicationRefill(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I'll help you refill your prescription"))
    }

    // ========== HEALTH & APPOINTMENT ACTIONS ==========

    private fun executeAppointmentCreate(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, CalendarActivity::class.java)
            intent.putExtra("action", "add")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening appointment scheduler"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't create appointment"))
        }
    }

    private fun executeAppointmentList(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, CalendarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening your appointments"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't open appointments"))
        }
    }

    private fun executeAppointmentCancel(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Are you sure you want to cancel this appointment?", requiresConfirmation = true))
    }

    private fun executeSOS(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, EmergencyActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Activating emergency assistance", requiresConfirmation = true))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to trigger SOS", e)
            onResult(ActionResult(false, "Couldn't activate emergency"))
        }
    }

    private fun executeHealthCheckin(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, HealthActivity::class.java)
            intent.putExtra("action", "checkin")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Starting health check-in"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't start health check"))
        }
    }

    private fun executeHealthSummary(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(context, HealthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening health summary"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't open health data"))
        }
    }

    private fun executeHealthRecord(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Recording your health data"))
    }

    private fun executeCaregiverContact(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val contactName = entities.contactName ?: "caregiver"
        onResult(ActionResult(true, "Calling your $contactName", requiresConfirmation = true))
    }

    // ========== CONVENIENCE ACTIONS ==========

    private fun executeQueryTime(onResult: (ActionResult) -> Unit) {
        val time = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        onResult(ActionResult(true, "The time is $time"))
    }

    private fun executeQueryDate(onResult: (ActionResult) -> Unit) {
        val date = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
        onResult(ActionResult(true, "Today is $date"))
    }

    private fun executeFindPhone(onResult: (ActionResult) -> Unit) {
        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val r = RingtoneManager.getRingtone(context, notification)
            r.play()
            onResult(ActionResult(true, "Playing ringtone... I am here!"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "I am here! (Couldn't play sound)"))
        }
    }

    private fun executeRepeatLast(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I'll repeat the last message"))
    }

    private fun executeReadScreen(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I'll read what's on screen"))
    }

    private fun executeBrightnessControl(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening display settings"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't open settings"))
        }
    }

    private fun executeDoNotDisturb(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening do not disturb settings"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't enable do not disturb"))
        }
    }

    private fun executeScreenLock(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening screen lock settings"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't open security settings"))
        }
    }

    // ========== WEB KNOWLEDGE ACTIONS ==========

    private fun executeWebSearch(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        try {
            val query = entities.rawText.replace(Regex("(search|find|look up|google)\\s+"), "")
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra("query", query)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Searching for $query"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't perform search"))
        }
    }

    private fun executeQAFact(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    private fun executeQAWeather(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra("query", "weather today")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Checking the weather"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't check weather"))
        }
    }

    private fun executeQANews(onResult: (ActionResult) -> Unit) {
        try {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra("query", "news today")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Opening today's news"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't get news"))
        }
    }

    private fun executeQADefinition(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    private fun executeQAHealthInfo(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    private fun executeSearchLocation(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        val location = entities.location ?: "places nearby"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$location"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            onResult(ActionResult(true, "Searching for $location"))
        } catch (e: Exception) {
            onResult(ActionResult(false, "Couldn't search location"))
        }
    }

    private fun executeQACalculation(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    private fun executeQAConversion(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    private fun executeQAMedicineInfo(entities: EntityExtractor.ExtractedEntities, onResult: (ActionResult) -> Unit) {
        executeWebSearch(entities, onResult)
    }

    // ========== SMALL TALK ACTIONS ==========

    private fun executeSmallTalkGreet(onResult: (ActionResult) -> Unit) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..11 -> "Good morning!"
            in 12..16 -> "Good afternoon!"
            in 17..20 -> "Good evening!"
            else -> "Hello!"
        }
        onResult(ActionResult(true, "$greeting How can I help you?"))
    }

    private fun executeSmallTalkThanks(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "You're welcome! Happy to help anytime."))
    }

    private fun executeSmallTalkFeelings(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I'm doing great! How are you today?"))
    }

    private fun executeSmallTalkJoke(onResult: (ActionResult) -> Unit) {
        val jokes = listOf(
            "Why don't scientists trust atoms? Because they make up everything!",
            "What do you call a bear with no teeth? A gummy bear!",
            "Why did the scarecrow win an award? He was outstanding in his field!"
        )
        onResult(ActionResult(true, jokes.random()))
    }

    private fun executeSmallTalkEncourage(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "You're doing great! Keep going, I believe in you!"))
    }

    private fun executeSmallTalkGoodbye(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "Goodbye! Have a wonderful day!"))
    }

    private fun executeSmallTalkHelp(onResult: (ActionResult) -> Unit) {
        onResult(ActionResult(true, "I can help you with calls, messages, medications, appointments, and much more. Just ask!"))
    }
    /**
     * Helper to find a phone number by name from System Contacts
     */
    private fun getPhoneNumber(name: String): String? {
        var phoneNumber: String? = null
        val contentResolver = context.contentResolver
        val uri = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)

        // Search for name containing the query (e.g., "Mom" finds "Mom", "Mom Mobile", etc.)
        val selection = "${android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
        val selectionArgs = arrayOf("%$name%")

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val numberIndex = it.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (numberIndex >= 0) {
                    phoneNumber = it.getString(numberIndex)
                    // Clean the number (remove dashes/spaces)
                    phoneNumber = phoneNumber?.replace(Regex("[^0-9+]"), "")
                }
            }
        }
        return phoneNumber
    }
}

