package com.example.thuc.network

import kotlinx.serialization.Serializable

@Serializable
data class HFMessage(
    val role: String,
    val content: String
)

@Serializable
data class TogetherAIRequest(
    val model: String = "deepseek-ai/DeepSeek-R1-Distill-Llama-70B-free",
    val messages: List<HFMessage>,
    val max_tokens: Int = 512
)

@Serializable
data class TogetherAIResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: HFMessage
)
