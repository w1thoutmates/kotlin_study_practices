package com.example.practice26.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardHTTPClient {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://deckofcardsapi.com/api/deck/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: CardRouter = retrofit.create(CardRouter::class.java)
    }
}