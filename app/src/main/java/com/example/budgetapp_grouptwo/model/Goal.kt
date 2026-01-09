package com.example.budgetapp_grouptwo.model

data class Goal(
    val id: String,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double = 0.0,
    val day: Int = 0,
    val month: Int = 0,
    val year: Int = 0
)