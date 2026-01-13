package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Income(
    id: Int,
    name: String,
    amount: Double,
    date: LocalDate,
    ): Cash(id,name,amount, date){

    var addedDate = date;

    override fun readOutLoud(): String{
        return "Income amount: ${amount}, date added: ${addedDate}";
    }
}