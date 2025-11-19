package com.denis.lab3.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {
    private val repository = DogRepository()

    private val _helloMessage = MutableStateFlow<String?>(null)
    val helloMessage: StateFlow<String?> = _helloMessage.asStateFlow()

    private val _calcResult = MutableStateFlow<Int?>(null)
    val calcResult: StateFlow<Int?> = _calcResult.asStateFlow()

    private val _dogMessage = MutableStateFlow<String?>(null)
    val dogMessage: StateFlow<String?> = _dogMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadHello(){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getHello()
            if(result.isSuccess){
                _helloMessage.value = result.getOrNull()?.get("message")
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }

    fun calculate(a: Int, b: Int){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.calculate(a, b)
            if(result.isSuccess){
                _calcResult.value = result.getOrNull()?.get("result")
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Calculation failed"
            }
            _isLoading.value = false
        }
    }

    fun addNewDog(name: String, breed: String){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.addDog(name, breed)

            if(result.isSuccess){
                _dogMessage.value = result.getOrNull()?.get("message") ?: "Dog added successfully"
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Failed to add dog"
            }
            _isLoading.value = false
        }
    }

    fun loadDog(){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getDog()

            if(result.isSuccess){
                _dogMessage.value = result.getOrNull()?.get("message")
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Failed to load dog"
            }
            _isLoading.value = false
        }
    }

    fun postWorld(){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.postWorld()

            if(result.isSuccess){
                _helloMessage.value = result.getOrNull()?.get("message")
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Post request failed"
            }
            _isLoading.value = false
        }
    }

    fun clearError(){
        _error.value = null
    }

    fun clearMessages(){
        _helloMessage.value = null
        _dogMessage.value = null
        _calcResult.value = null
    }
}