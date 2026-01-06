package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Expense (
        amount: Int,
        date: LocalDate,
        type: ExpenseType
    ): Cash(amount, date){
    var amount = amount;
    var type = type;
    var dateAdded = date;

    override fun readOutLoud(){
        println("amount: ${amount}, Expense type is ${type.toString()}, date added: ${dateAdded}")
    }

}