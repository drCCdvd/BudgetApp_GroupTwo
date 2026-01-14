package com.example.budgetapp_grouptwo.model

import android.R
import java.time.*

abstract class Cash (
        id: Int,
        name: String,
        amount: Double,
        dateAdded: LocalDate
    ) {
    var id = id;
    var name = name;
    var amount = amount;
    var dateAdded = dateAdded;

    open fun readOutLoud(): String {
        return ("amount ${amount} added at ${dateAdded}");
    }

    /** Saves the cash type in data repository
     *
     */
    fun save(){
        //Todo implement later
    }

    /** Deletes the cash type from data repository
     *
     */
    fun delete(){
        //Todo implement later
    }
}