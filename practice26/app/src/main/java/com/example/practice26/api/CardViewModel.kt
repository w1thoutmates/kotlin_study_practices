package com.example.practice26.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    private val repository = CardRepository()

    private val _card = MutableStateFlow<Card?>(null)
    val card: StateFlow<Card?> = _card.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadRandomCard() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getRandomCard()
            if (result.isSuccess) {
                _card.value = result.getOrNull()?.cards?.firstOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Unhanded error"
            }
            _isLoading.value = false
        }
    }
}