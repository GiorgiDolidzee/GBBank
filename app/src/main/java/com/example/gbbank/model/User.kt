package com.example.gbbank.model

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val balance: Double? = 0.0
)
