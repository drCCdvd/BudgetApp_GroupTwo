package com.example.budgetapp_grouptwo.model
import java.time.LocalDate
import androidx.room.Entity
import androidx.room.PrimaryKey

//Extends Cash object
class Expense (
    id: Int=0,
    name: String,
    amount: Double,
    date: LocalDate,
    type: ExpenseType,
    ): Cash(id,name, amount, date){

    //Includes an extra variable: ExpenseType
    var type = type;

    override fun readOutLoud(): String{
        return "amount: ${amount}, Expense type is ${type.toString()}, date added: ${dateAdded}";
    }

}