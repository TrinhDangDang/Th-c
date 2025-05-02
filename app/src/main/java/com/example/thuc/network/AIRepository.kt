package com.example.thuc.network

import android.util.Log
import kotlinx.serialization.json.Json
import retrofit2.HttpException


interface AIRepository {
    suspend fun getQuote(prompt: String): String
}

class TogetherAIRepository(private val apiService: TogetherAiApiService): AIRepository {
    override suspend fun getQuote(prompt: String): String {
        val token = "Bearer "
        val request = TogetherAIRequest(
            //model = "deepseek-ai/DeepSeek-R1-Distill-Llama-70B-free", // ✅ EXPLICITLY set
            messages = listOf(HFMessage(role = "user", content = prompt))
        )


        return try {
            val json = Json { encodeDefaults = true }
            Log.d("TogetherRequest", json.encodeToString(TogetherAIRequest.serializer(), request))

            val response = apiService.generateQuote(token, request)
            response.choices.firstOrNull()?.message?.content ?: "No response."
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("TogetherError", "HTTP ${e.code()} - ${e.message()}\n$errorBody")
            } else {
                Log.e("TogetherError", "Unexpected error", e)
            }
            return "Error: ${e.message}"
        }

        }
    }

