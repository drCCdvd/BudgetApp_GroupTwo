package com.example.budgetapp_grouptwo.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.ExpenseType
import java.time.LocalDate


import androidx.compose.runtime.mutableStateListOf



class RecentViewModel : ViewModel() {

    private val cashFlow = CashFlow()

    val recentItems: SnapshotStateList<Cash> = cashFlow.cashFlows


    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        cashFlow.addNewIncome("Løn", 15000.0, LocalDate.now().minusDays(1))
        cashFlow.addNewExpense("Mad", 120.0, ExpenseType.FOOD, LocalDate.now().minusDays(2))
        cashFlow.addNewExpense("Transport", 50.0, ExpenseType.TRANSPORT, LocalDate.now().minusDays(3))
        cashFlow.addNewIncome("Freelance", 2000.0, LocalDate.now().minusDays(4))
        cashFlow.addNewIncome("Freelance", 1000.0, LocalDate.now().minusDays(5))
    }

    fun removeItem(id: Int) {
        cashFlow.removeItem(id)
    }


}
