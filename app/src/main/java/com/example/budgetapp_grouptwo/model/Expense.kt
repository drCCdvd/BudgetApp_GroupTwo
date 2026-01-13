package com.example.budgetapp_grouptwo.model

import com.example.bugetapp_grouptwo.RegularCashFlow
import java.time.LocalDate

class Expense (
    id: Int,
    name: String,
    amount: Double,
    date: LocalDate,
    type: ExpenseType,
    ): Cash(id,name, amount, date)

/*
{



    override fun readOutLoud(): String{
        return "amount: ${amount}, date added: $dateAdded";
    }

}
 */