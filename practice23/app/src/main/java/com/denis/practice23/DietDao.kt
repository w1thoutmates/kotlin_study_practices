package com.denis.practice23


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DietDao {
    @Query("SELECT * FROM Diet")
    fun getAll(): LiveData<List<Diet>>

    @Insert
    fun add(vararg diets: Diet)
}