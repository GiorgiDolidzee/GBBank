package com.example.gbbank.model

data class User(
    var firstName: String? = "",
    var lastName: String? = "",
    var email: String? = "",
    var photo: String? = "",
    var balance: Double? = 0.0
)
