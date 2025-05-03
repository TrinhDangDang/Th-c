package com.example.thuc.network

import android.util.Log
import com.example.thuc.data.Quote
import kotlinx.serialization.json.Json
import retrofit2.HttpException


interface AIRepository {
    suspend fun getQuote(prompt: String): Quote
}

class TogetherAIRepository(private val apiService: TogetherAiApiService): AIRepository {
    override suspend fun getQuote(prompt: String): Quote {
        val token = ""
        val request = TogetherAIRequest(
            //model = "deepseek-ai/DeepSeek-R1-Distill-Llama-70B-free", // ✅ EXPLICITLY set
            messages = listOf(
                HFMessage("system", "Always return a new, unique quote. Avoid repetition."),
                HFMessage(role = "user", content = prompt)
            )
        )


        return try {
            val json = Json { encodeDefaults = true }
            Log.d("TogetherRequest", json.encodeToString(TogetherAIRequest.serializer(), request))

            val response = apiService.generateQuote(token, request)
            val rawContent = response.choices.firstOrNull()?.message?.content ?: return Quote(text = "No response.", author = "Unknown")

            // Remove <think> section if present
            val content = rawContent.substringAfter("</think>").trim().ifEmpty { rawContent.trim() }

            val quoteRegex = Regex("^\"(.+?)\"\\s+[—-]\\s+(.*)$")
            val match = quoteRegex.find(content)

            val (quoteText, quoteAuthor) = if (match != null) {
                match.groupValues[1] to match.groupValues[2]
            } else {
                content to "Unknown"
            }


            Quote(text = quoteText, author = quoteAuthor)
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("TogetherError", "HTTP ${e.code()} - ${e.message()}\n$errorBody")
            } else {
                Log.e("TogetherError", "Unexpected error", e)
            }
            return   Quote(text = "Error: ${e.message}", author = "Error")
        }

        }
    }

