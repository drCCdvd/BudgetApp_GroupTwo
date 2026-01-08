package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Expense (
    id: Int,
    name: String,
    amount: Double,
    date: LocalDate,
    type: ExpenseType,
    ): Cash(id,name, amount, date){


    var type = type;

    override fun readOutLoud(): String{
        return "amount: ${amount}, Expense type is ${type.toString()}, date added: ${dateAdded}";
    }

}