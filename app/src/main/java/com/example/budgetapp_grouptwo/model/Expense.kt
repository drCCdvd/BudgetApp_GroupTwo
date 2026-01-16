package com.example.budgetapp_grouptwo.model
import java.time.LocalDate
import androidx.room.Entity
import androidx.room.PrimaryKey

class Expense (
    id: Int=0,
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