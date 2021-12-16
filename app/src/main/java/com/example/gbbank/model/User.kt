package com.example.gbbank.model

data class User(
    var firstName: String? = "",
    var lastName: String? = "",
    var email: String? = "",
    var balance: Double? = 0.0,
    var transactions: ArrayList<Transaction>? = arrayListOf()
) {
    data class Transaction(
        var amount: String? = "",
        var date: String? = ""
    )
}
