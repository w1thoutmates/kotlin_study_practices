package com.denis.practice23

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DietVM(application: Application): ViewModel() {
    val dietList: LiveData<List<Diet>>
    private val repository: DietRepository
    var id by mutableStateOf(0)
    var type by mutableStateOf("")
    var description by mutableStateOf("")

    init {
        val dietDb = DataMammal.getInstance(application)
        val dietDao = dietDb.dietDao()
        repository = DietRepository(dietDao)
        dietList = repository.dietList
    }

    fun changeId(value: String){
        id = value.toIntOrNull() ?: id
    }

    fun changeType(value: String){
        type = value
    }

    fun changeDescription(value: String){
        description = value
    }

    fun add(){
        repository.addDiet(
            Diet(
                dietType = type,
                description = description
            )
        )
    }
}