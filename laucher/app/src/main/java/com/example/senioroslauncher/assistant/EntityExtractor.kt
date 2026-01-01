package com.example.senioroslauncher.assistant

import android.util.Log
import com.example.senioroslauncher.data.database.entity.MedicationFrequency
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extract entities from user commands (names, times, numbers, etc.)
 */
class EntityExtractor {

    companion object {
        private const val TAG = "EntityExtractor"
    }

    data class ExtractedEntities(
        val contactName: String? = null,
        val phoneNumber: String? = null,
        val time: Calendar? = null,
        val duration: Int? = null, // raw number found
        val appName: String? = null,
        val medicationName: String? = null,
        val dosage: String? = null,               // For adding meds
        val frequency: MedicationFrequency? = null, // For adding meds
        val number: Int? = null,
        val location: String? = null,
        val destination: String? = null,          // NEW: For Ride Booking
        val rawText: String
    )

    /**
     * Extract entities based on intent
     */
    fun extract(text: String, intent: String): ExtractedEntities {
        Log.d(TAG, "Extracting entities from: '$text' for intent: $intent")

        return when (intent) {
            "CALL_CONTACT", "SEND_MESSAGE", "CAREGIVER_CONTACT" -> {
                ExtractedEntities(
                    contactName = extractContactName(text),
                    rawText = text
                )
            }
            "SET_ALARM" -> {
                ExtractedEntities(
                    time = extractTime(text),
                    rawText = text
                )
            }
            "SET_TIMER" -> {
                ExtractedEntities(
                    duration = extractDuration(text),
                    rawText = text
                )
            }
            "OPEN_APP" -> {
                ExtractedEntities(
                    appName = extractAppName(text),
                    rawText = text
                )
            }
            "MEDICATION_ADD" -> {
                ExtractedEntities(
                    medicationName = extractMedicationName(text),
                    dosage = extractDosage(text),
                    frequency = extractFrequency(text),
                    rawText = text
                )
            }
            "MEDICATION_LOG_TAKEN", "MEDICATION_QUERY" -> {
                ExtractedEntities(
                    medicationName = extractMedicationName(text),
                    rawText = text
                )
            }
            "SEARCH_LOCATION", "APPOINTMENT_CREATE" -> {
                ExtractedEntities(
                    location = extractLocation(text),
                    time = if (intent == "APPOINTMENT_CREATE") extractTime(text) else null,
                    rawText = text
                )
            }
            "HEALTH_RECORD" -> {
                ExtractedEntities(
                    number = extractNumber(text),
                    rawText = text
                )
            }
            // NEW: Ride Booking extraction
            "BOOK_RIDE" -> {
                ExtractedEntities(
                    destination = extractDestination(text),
                    rawText = text
                )
            }
            else -> ExtractedEntities(rawText = text)
        }
    }

    private fun extractContactName(text: String): String? {
        val lowerText = text.lowercase()
        val cleaned = lowerText
            .replace(Regex("(call|phone|dial|ring|message|text|whatsapp|sms|contact)\\s+"), "")
            .replace(Regex("\\s+(please|now)"), "")
            .trim()

        val myPattern = Regex("my\\s+(\\w+)")
        val myMatch = myPattern.find(cleaned)
        if (myMatch != null) {
            return myMatch.groupValues[1].capitalize()
        }

        val words = cleaned.split(" ")
        return words.firstOrNull()?.capitalize()
    }

    /**
     * Updated to handle LLM 24-hour format and auto-scheduling for tomorrow
     */
    private fun extractTime(text: String): Calendar? {
        val lowerText = text.lowercase()
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance()

        // Format A: "HH:mm" (24-hour, e.g., "06:00", "14:30") - Common from LLM
        val pattern24 = Regex("(\\d{1,2}):(\\d{2})")
        val match24 = pattern24.find(lowerText)

        if (match24 != null) {
            val hour = match24.groupValues[1].toInt()
            val minute = match24.groupValues[2].toInt()

            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            // If time has passed today, schedule for tomorrow
            if (calendar.before(now)) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
            return calendar
        }

        // Format B: "h:mm am/pm" (e.g., "6:30 pm")
        val patternAmPm = Regex("(\\d{1,2})[:\\s]*(\\d{0,2})\\s*(am|pm)")
        val matchAmPm = patternAmPm.find(lowerText)

        if (matchAmPm != null) {
            val hour = matchAmPm.groupValues[1].toInt()
            val minuteStr = matchAmPm.groupValues[2]
            val minute = if (minuteStr.isNotEmpty()) minuteStr.toInt() else 0
            val amPm = matchAmPm.groupValues[3]

            var finalHour = hour
            if (amPm == "pm" && hour != 12) finalHour += 12
            if (amPm == "am" && hour == 12) finalHour = 0

            calendar.set(Calendar.HOUR_OF_DAY, finalHour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.before(now)) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
            return calendar
        }

        return null
    }

    /**
     * Updated to return raw number without pre-converting seconds to minutes
     */
    private fun extractDuration(text: String): Int? {
        val lowerText = text.lowercase()
        val numberPattern = Regex("(\\d+)")
        val match = numberPattern.find(lowerText)
        return match?.groupValues?.get(1)?.toInt()
    }

    private fun extractAppName(text: String): String? {
        val lowerText = text.lowercase()
        val cleaned = lowerText
            .replace(Regex("(open|launch|start|run)\\s+"), "")
            .trim()

        return when {
            cleaned.contains("whatsapp") -> "WhatsApp"
            cleaned.contains("youtube") -> "YouTube"
            cleaned.contains("maps") || cleaned.contains("google maps") -> "Google Maps"
            cleaned.contains("gmail") -> "Gmail"
            cleaned.contains("facebook") -> "Facebook"
            cleaned.contains("instagram") -> "Instagram"
            cleaned.contains("chrome") -> "Chrome"
            cleaned.contains("spotify") -> "Spotify"
            cleaned.contains("messenger") -> "Messenger"
            cleaned.contains("twitter") -> "Twitter"
            cleaned.contains("telegram") -> "Telegram"
            cleaned.contains("linkedin") -> "LinkedIn"
            cleaned.contains("netflix") -> "Netflix"
            else -> cleaned.split(" ").firstOrNull()?.capitalize()
        }
    }

    private fun extractMedicationName(text: String): String? {
        val lowerText = text.lowercase()
        // Remove common words to isolate the med name
        val cleaned = lowerText
            .replace(Regex("(i took|took|take|add|medicine|pill|medication|tablet|my)\\s+"), "")
            .trim()

        // Return first word as the medication name (e.g. "Metformin")
        return cleaned.split(" ").firstOrNull()?.capitalize()
    }

    /**
     * Extracts dosage including full words (milligrams, tablets, etc.)
     */
    private fun extractDosage(text: String): String? {
        val lowerText = text.lowercase()

        // Regex matches:
        // 1. Number (int or decimal)
        // 2. Optional space
        // 3. Unit (mg, milligrams, tablet, etc.)
        val dosagePattern = Regex("(\\d+(\\.\\d+)?)\\s*(mg|milligrams?|g|grams?|ml|milliliters?|mcg|micrograms?|tablets?|pills?|capsules?|drops?)")

        val match = dosagePattern.find(lowerText)
        return match?.value
    }

    private fun extractFrequency(text: String): MedicationFrequency? {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("daily") || lowerText.contains("every day") -> MedicationFrequency.DAILY
            lowerText.contains("weekly") || lowerText.contains("every week") -> MedicationFrequency.WEEKLY
            lowerText.contains("monthly") || lowerText.contains("every month") -> MedicationFrequency.MONTHLY
            lowerText.contains("needed") || lowerText.contains("pain") -> MedicationFrequency.AS_NEEDED
            else -> null
        }
    }

    private fun extractLocation(text: String): String? {
        val lowerText = text.lowercase()
        val cleaned = lowerText
            .replace(Regex("(nearest|nearby|find|search|locate)\\s+"), "")
            .trim()

        return when {
            cleaned.contains("hospital") -> "hospital"
            cleaned.contains("pharmacy") || cleaned.contains("medical store") -> "pharmacy"
            cleaned.contains("clinic") -> "clinic"
            cleaned.contains("doctor") -> "doctor"
            cleaned.contains("emergency") -> "emergency room"
            else -> cleaned
        }
    }

    /**
     * NEW: Helper for Ride Booking
     */
    private fun extractDestination(text: String): String? {
        val lowerText = text.lowercase()

        // Check for saved places keywords
        if (lowerText.contains("home")) return "home"
        if (lowerText.contains("doctor") || lowerText.contains("clinic") || lowerText.contains("hospital")) return "doctor"
        if (lowerText.contains("pharmacy") || lowerText.contains("chemist") || lowerText.contains("drug store")) return "pharmacy"

        // Extract general destination using "to"
        // e.g. "Ride to the mall" -> "the mall"
        val toPattern = Regex("(to|goto)\\s+(.+)")
        val match = toPattern.find(lowerText)
        if (match != null) {
            return match.groupValues[2].trim()
        }

        return null
    }

    private fun extractNumber(text: String): Int? {
        val numberPattern = Regex("(\\d+)")
        val match = numberPattern.find(text)
        return match?.groupValues?.get(1)?.toIntOrNull()
    }

    private fun String.capitalize(): String {
        return replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}