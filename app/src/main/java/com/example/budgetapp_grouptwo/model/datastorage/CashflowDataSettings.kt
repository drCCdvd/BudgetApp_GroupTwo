package com.example.budgetapp_grouptwo.model.datastorage

import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Goal
import kotlinx.serialization.Serializable

@Serializable
data class CashflowDataSettings(
    val regularEarnings: Double,
    val regularExpenses: Double,
    val cashFlows: List<Cash>,
    val goals: List<Goal>,
)
