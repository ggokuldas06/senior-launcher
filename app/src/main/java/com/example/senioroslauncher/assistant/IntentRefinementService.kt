package com.example.senioroslauncher.assistant

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Service to communicate with the LLM Intent Refinement Server
 * Provides fallback classification when TFLite model confidence is low
 */
class IntentRefinementService(
    private val serverIp: String = "192.168.0.100", // Replace with your server IP
    private val serverPort: Int = 8000
) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    companion object {
        private const val TAG = "IntentRefinementService"
        private const val ENDPOINT = "/refine"
    }

    @Serializable
    data class RefineRequest(
        val text: String
    )

    @Serializable
    data class RefineResponse(
        val intent: String,
        val reply: String,
        val entities: Map<String, String?> = emptyMap()
    )

    data class RefinementResult(
        val success: Boolean,
        val intent: String?,
        val reply: String?,
        val entities: Map<String, String?> = emptyMap(),
        val error: String? = null
    )

    /**
     * Check if server is reachable
     */
    suspend fun checkServerConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = "http://$serverIp:$serverPort/"
            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                val isSuccessful = response.isSuccessful
                Log.d(TAG, "Server connection check: ${if (isSuccessful) "SUCCESS" else "FAILED"}")
                return@withContext isSuccessful
            }
        } catch (e: Exception) {
            Log.e(TAG, "Server connection failed", e)
            return@withContext false
        }
    }

    /**
     * Refine intent using LLM server
     */
    suspend fun refineIntent(text: String): RefinementResult = withContext(Dispatchers.IO) {
        try {
            val url = "http://$serverIp:$serverPort$ENDPOINT"

            val requestBody = RefineRequest(text)
            val requestJson = json.encodeToString(RefineRequest.serializer(), requestBody)

            Log.d(TAG, "Sending request to server: $url")
            Log.d(TAG, "Request body: $requestJson")

            val body = requestJson.toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Server returned error: ${response.code}")
                    return@withContext RefinementResult(
                        success = false,
                        intent = null,
                        reply = null,
                        error = "Server error: ${response.code}"
                    )
                }

                val responseBody = response.body?.string()
                if (responseBody == null) {
                    Log.e(TAG, "Empty response from server")
                    return@withContext RefinementResult(
                        success = false,
                        intent = null,
                        reply = null,
                        error = "Empty response"
                    )
                }

                Log.d(TAG, "Server response: $responseBody")

                val refinedResponse = json.decodeFromString(
                    RefineResponse.serializer(),
                    responseBody
                )

                Log.d(TAG, "Refined intent: ${refinedResponse.intent}, reply: ${refinedResponse.reply}")

                return@withContext RefinementResult(
                    success = true,
                    intent = refinedResponse.intent,
                    reply = refinedResponse.reply,
                    entities = refinedResponse.entities
                )
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error during refinement", e)
            return@withContext RefinementResult(
                success = false,
                intent = null,
                reply = null,
                error = "Network error: ${e.message}"
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error during refinement", e)
            return@withContext RefinementResult(
                success = false,
                intent = null,
                reply = null,
                error = "Error: ${e.message}"
            )
        }
    }

    /**
     * Test the server with a sample request
     */
    suspend fun testServer(): Boolean = withContext(Dispatchers.IO) {
        try {
            val result = refineIntent("hello")
            val isSuccessful = result.success
            Log.d(TAG, "Server test: ${if (isSuccessful) "PASSED" else "FAILED"}")
            return@withContext isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "Server test failed", e)
            return@withContext false
        }
    }
}