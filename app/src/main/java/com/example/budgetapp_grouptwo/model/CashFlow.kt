package com.example.budgetapp_grouptwo.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.example.budgetapp_grouptwo.CashFlowStorage
import com.example.budgetapp_grouptwo.RegularCashFlow


import java.time.LocalDate

class CashFlow () {
    val regularCashFlow = RegularCashFlow
    val cashFlows = mutableStateListOf<Cash>()
    val cashFlowStorage: CashFlowStorage = CashFlowStorage

    fun loadRegularCashFlow(context: Context) {
        regularCashFlow.setRegularEarnings(cashFlowStorage.loadRegularEarnings(context))
        regularCashFlow.setRegularExpense(cashFlowStorage.loadRegularExpenses(context))
        //Implementer cashFlowStorage.loadCashflow();
    }

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
        var disposable = 0.0;
        disposable += regularCashFlow.getRegularEarnings()-regularCashFlow.getRegularExpenses()
        return disposable;
    }

}