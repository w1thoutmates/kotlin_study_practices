package com.denis.lab3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class DogHTTPClient {
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: DogRouter = retrofit.create(DogRouter::class.java)
    }
}