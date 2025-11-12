package com.denis.practice23

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MammalDao {
    @Query("SELECT * FROM Mammal")
    fun getAll(): LiveData<List<Mammal>>

    @Query("SELECT Mammal.* FROM Mammal INNER JOIN Diet ON Mammal.mammalDietId = Diet.dietId WHERE Diet.dietType = :dietType")
    fun getMammalsByDietType(dietType: String): LiveData<List<Mammal>>

    @Insert
    fun add(vararg mammals: Mammal)

    @Delete
    fun delete(mammal: Mammal)

    @Query("DELETE FROM Mammal WHERE mammalId=:id")
    fun deleteById(id: Int)

    @Query("UPDATE Mammal SET mammalName=:mammalName, species=:species, mammalDietId=:mammalDietId WHERE mammalId=:id")
    fun updateById(id: Int, mammalName: String, species: String, mammalDietId: Int)
}