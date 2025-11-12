package com.denis.practice23


import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MammalRepository(private val mammalDao: MammalDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val mammalList: LiveData<List<Mammal>> = mammalDao.getAll()

    fun addMammal(mammal: Mammal){
        coroutineScope.launch {
            mammalDao.add(mammal)
        }
    }

    fun deleteMammal(id: Int){
        coroutineScope.launch {
            mammalDao.deleteById(id)
        }
    }

    fun updateMammal(id: Int, mammalName: String, species: String, mammalDietId: Int){
        coroutineScope.launch {
            mammalDao.updateById(id, mammalName, species, mammalDietId)
        }
    }

    fun getMammalsByDietType(type: String): LiveData<List<Mammal>> {
        return mammalDao.getMammalsByDietType(type)
    }
}