package com.example.budgetapp_grouptwo.model

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate
import kotlin.math.exp

class CashFlow (
    //Todo RegularCashFlow object here
    ) {
    //regularCashFlow: RegularCashFlow = regularCashFlow
    var cashFlows = mutableStateListOf<Cash>();

    fun addNewExpense(name: String, amount: Double, type: ExpenseType, date: LocalDate){

        var nextId = cashFlows.size;
        var newExpense = Expense(nextId,name,amount, date, type)
        cashFlows.add(newExpense)
    }

    fun addNewIncome(name: String, amount: Double, date: LocalDate){
        val nextId = cashFlows.size;
        var newIncome = Income(nextId,name,amount, date)
        cashFlows.add(newIncome)
    }

    fun getDisposablex(startDate: LocalDate, endDate: LocalDate): Double{
        return 0.0;
    }

}