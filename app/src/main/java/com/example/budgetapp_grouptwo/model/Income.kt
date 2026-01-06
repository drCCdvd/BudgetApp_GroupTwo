package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Income(
        amount: Int,
        date: LocalDate
    ): Cash(amount, date){

    var amount = amount;
    var addedDate = date;

    override fun readOutLoud(){
        println("Income amount: ${amount}, date added: ${addedDate}")
    }
}