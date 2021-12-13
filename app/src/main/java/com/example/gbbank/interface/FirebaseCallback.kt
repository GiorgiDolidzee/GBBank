package com.example.gbbank.`interface`

import com.example.gbbank.model.Response

interface FirebaseCallback {
    fun onResponse(response: Response)
}