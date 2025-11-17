package com.example.practice26.api

data class RandomCard(
    val success: Boolean,
    val cards: List<Card>,
    val remaining: Int
)

data class Card(
    val image: String,
    val value: String,
    val suit: String,
    val code: String
)