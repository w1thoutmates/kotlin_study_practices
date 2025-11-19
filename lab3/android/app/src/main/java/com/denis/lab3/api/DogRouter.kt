package com.denis.lab3.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DogRouter {
    @GET("/")
    suspend fun getHello(): Response<Map<String, String>>

    @POST("/postworld")
    suspend fun postWorld(): Response<Map<String, String>>

    @GET("/calc")
    suspend fun calculate(
        @Query("a") a: Int,
        @Query("b") b: Int
    ): Response<Map<String, Int>>

    @FormUrlEncoded
    @POST("/add_dog")
    suspend fun addDog(
        @Field("name") name: String,
        @Field("breed") breed: String
    ): Response<Map<String, String>>

    @GET("/get_dog")
    suspend fun getDog(): Response<Map<String, String>>
}