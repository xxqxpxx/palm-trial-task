package com.example.palmtest.legacy.data

data class Message(
    val id: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)