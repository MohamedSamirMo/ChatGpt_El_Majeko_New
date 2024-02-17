package com.example.chatgptelmajeko.models.request

data class lmageGenerationRequest(
    val n: Int,
    val prompt: String,
    val size: String
)