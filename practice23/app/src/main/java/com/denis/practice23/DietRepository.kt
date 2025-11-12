package com.denis.practice23

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DietRepository(private val dietDao: DietDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val dietList: LiveData<List<Diet>> = dietDao.getAll()

    fun addDiet(diet: Diet){
        coroutineScope.launch {
            dietDao.add(diet)
        }
    }
}