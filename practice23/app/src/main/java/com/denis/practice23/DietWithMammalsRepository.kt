package com.denis.practice23

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DietWithMammalsRepository(private val dietWithMammalsDao: DietWithMammalsDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val dietWithMammalsList: LiveData<List<DietWithMammals>> = dietWithMammalsDao.getAll()

}