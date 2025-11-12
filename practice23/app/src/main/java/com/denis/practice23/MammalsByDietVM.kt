package com.denis.practice23

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MammalsByDietVM(val application: Application) : ViewModel() {
    private val repository: MammalRepository

    init{
        val mammalDb = DataMammal.getInstance(application)
        val dietDao = mammalDb.mammalDao()
        repository = MammalRepository(dietDao)
    }

    fun getMammalsByDietType(type: String): LiveData<List<Mammal>> {
        return repository.getMammalsByDietType(type)
    }
}
