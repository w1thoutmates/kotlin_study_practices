package com.example.practice26.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardRepository {
    private val api = CardHTTPClient.api

    suspend fun getRandomCard(): Result<RandomCard> = withContext(Dispatchers.IO) {
        try {
            val response = api.getRandomCard()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("HTTP Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}