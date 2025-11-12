package com.denis.practice23

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DietWithMammalsDao {
    @Transaction
    @Query("SELECT * FROM Diet")
    fun getAll(): LiveData<List<DietWithMammals>>
}