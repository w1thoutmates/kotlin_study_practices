package com.denis.practice23

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MammalVM(application: Application): ViewModel() {
    val mammalList: LiveData<List<Mammal>>
    private val repository: MammalRepository
    var mammalId by mutableStateOf(0)
    var mammalName by mutableStateOf("")
    var mammalSpecies by mutableStateOf("")
    var mammalDietId by mutableStateOf(0)

    init{
        val mammalDb = DataMammal.getInstance(application)
        val mammalDao = mammalDb.mammalDao()
        repository = MammalRepository(mammalDao)
        mammalList = repository.mammalList
    }

    fun changeId(value: String){
        mammalId = value.toIntOrNull() ?: mammalId
    }

    fun changeDietId(value: String){
        mammalDietId = value.toIntOrNull() ?: mammalDietId
    }

    fun changeName(value: String){
        mammalName = value
    }

    fun changeSpecies(value: String){
        mammalSpecies = value
    }

    fun addMammal(){
        repository.addMammal(
            Mammal(
            mammalName = mammalName,
            species = mammalSpecies,
            mammalDietId = mammalDietId
            )
        )
    }

    fun updateMammal(){
        repository.updateMammal(
            id = mammalId,
            mammalName = mammalName,
            species = mammalSpecies,
            mammalDietId = mammalDietId
        )
    }

    fun deleteMammal(id: Int){
        repository.deleteMammal(id)
    }

}