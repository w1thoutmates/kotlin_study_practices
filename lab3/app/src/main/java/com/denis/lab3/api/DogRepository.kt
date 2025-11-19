package com.denis.lab3.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    private val api = DogHTTPClient.api

    suspend fun getHello(): Result<Map<String, String>> = withContext(Dispatchers.IO){
        try{
            val response = api.getHello()
            if(response.isSuccessful)
                Result.success(response.body() ?: emptyMap())
            else
                Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun postWorld(): Result<Map<String, String>> = withContext(Dispatchers.IO){
        try {
            val response = api.postWorld()
            if(response.isSuccessful){
                Result.success(response.body() ?: emptyMap())
            }
            else{
                Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
            }
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun calculate(a: Int, b: Int): Result<Map<String, Int>> = withContext(Dispatchers.IO){
        try{
            val response = api.calculate(a, b)
            if(response.isSuccessful){
                Result.success(response.body() ?: emptyMap())
            }
            else{
                Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
            }
        }
        catch (e: Exception){
            Result.failure(e)

        }
    }

    suspend fun addDog(name: String, breed: String): Result<Map<String, String>> = withContext(Dispatchers.IO){
        try{
            val response = api.addDog(name, breed)
            if(response.isSuccessful){
                Result.success(response.body() ?: emptyMap())
            }
            else{
                Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
            }
        }
        catch(e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getDog(): Result<Map<String, String>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getDog()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyMap())
            } else {
                Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}