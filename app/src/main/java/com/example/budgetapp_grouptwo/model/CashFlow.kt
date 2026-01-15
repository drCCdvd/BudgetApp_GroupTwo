package com.example.budgetapp_grouptwo.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.example.budgetapp_grouptwo.CashFlowStorage
import com.example.budgetapp_grouptwo.RegularCashFlow


import java.time.LocalDate
import java.time.Month
import java.time.temporal.ChronoUnit

class CashFlow () {
    val regularCashFlow = RegularCashFlow
    val cashFlows = mutableStateListOf<Cash>()
    val cashFlowStorage: CashFlowStorage = CashFlowStorage

    fun loadRegularCashFlow(context: Context) {
        regularCashFlow.setRegularEarnings(cashFlowStorage.loadRegularEarnings(context))
        regularCashFlow.setRegularExpense(cashFlowStorage.loadRegularExpenses(context))
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

    fun getDisposable(startDate: LocalDate, endDate: LocalDate, context: Context): Double{
        var disposable = 0.0;

        var intervalInMonths = ChronoUnit.MONTHS.between(startDate,endDate)+1;
        println(intervalInMonths);

        loadRegularCashFlow(context)

        println("${regularCashFlow.getRegularExpenses()}, ${regularCashFlow.getRegularEarnings()}")
        //Faste:
        disposable += intervalInMonths*regularCashFlow.getRegularEarnings()-intervalInMonths*regularCashFlow.getRegularExpenses()

        //Variable
        for(cash in cashFlows){
            if(cash.dateAdded.isAfter(startDate) && cash.dateAdded.isBefore(endDate)){
                if(cash is Expense){
                    disposable-=cash.amount;
                }else{
                    disposable+=cash.amount;
                }
            }
        }

        print(disposable)

        return disposable;
    }

}