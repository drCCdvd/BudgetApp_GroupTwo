package com.example.budgetapp_grouptwo.model

data class Goal(
    val name: String,
    val targetAmount: Double,
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0
)