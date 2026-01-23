package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

data class Goal(
    val id: Int = 0,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double = 0.0,
    val createdDate: LocalDate,
    val endDate: LocalDate,
    val colorindex: Int = id % 3 //For the color of the progress-bar
)