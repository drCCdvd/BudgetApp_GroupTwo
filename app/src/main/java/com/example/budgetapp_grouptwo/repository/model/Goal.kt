package com.example.budgetapp_grouptwo.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity
data class Goal(
    @PrimaryKey(true)
    @ColumnInfo("uid")
    val uid: Int=0,

    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val createdDate: LocalDate,
    val endDate: LocalDate,
)
