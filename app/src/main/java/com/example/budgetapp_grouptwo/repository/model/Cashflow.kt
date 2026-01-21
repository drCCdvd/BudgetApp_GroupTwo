package com.example.budgetapp_grouptwo.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Cashflow (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("uid")
    val uid: Int=0,

    val name: String,

    val amount: Double,

    val createdDate: LocalDate,

    val type: CashflowType,
)
