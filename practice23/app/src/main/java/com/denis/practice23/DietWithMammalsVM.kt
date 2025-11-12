package com.denis.practice23

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DietWithMammalsVM(application: Application) : ViewModel() {
    val dietWithMammalsList: LiveData<List<DietWithMammals>>
    private val repository: DietWithMammalsRepository

    init {
        val dietDb = DataMammal.getInstance(application)
        val dietDao = dietDb.dietWithMammalsDao()
        repository = DietWithMammalsRepository(dietDao)
        dietWithMammalsList = repository.dietWithMammalsList
    }
}