package com.example.practice26.api

import retrofit2.Response
import retrofit2.http.GET

interface CardRouter {
    @GET("new/draw/?count=1")
    suspend fun getRandomCard(): Response<RandomCard>
}