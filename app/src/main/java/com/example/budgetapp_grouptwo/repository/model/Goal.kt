package com.example.budgetapp_grouptwo.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity
data class Goal(
    @PrimaryKey(true) val uid: Int=0,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val createdDate: String,
    val endDate: String,
)
