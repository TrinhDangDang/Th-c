package com.example.thuc.network

import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.http.Header

interface TogetherAiApiService {
    @POST("chat/completions")
    suspend fun generateQuote(
        @Header("Authorization") auth: String,
        @Body request: TogetherAIRequest
    ): TogetherAIResponse
}

