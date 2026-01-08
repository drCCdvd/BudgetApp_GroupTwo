package com.example.budgetapp_grouptwo.ViewModel

import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.ExpenseType
import java.time.LocalDate

class NewCashflowViewModel(): ViewModel() {
    //Initialize cash flow object
    var cashFlow = CashFlow();

    fun addExpense(name: String, amount: Double, date: LocalDate, type: ExpenseType){
        cashFlow.addNewExpense(name, amount, type, date);
    }

    fun addIncome(name: String, amount: Double, date: LocalDate){
        cashFlow.addNewIncome(name, amount, date);
    }

    //Returns cashflow data
    fun getCashflowData(){
        //todo implement
    }
}