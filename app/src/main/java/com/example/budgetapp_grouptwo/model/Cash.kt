package com.example.budgetapp_grouptwo.model

import android.R
import java.time.*

abstract class Cash (
        amount: Int,
        dateAdded: LocalDate
    ) {
    private var amount = amount;
    private var dateAdded = dateAdded;

    fun readOutLoud() {
        println("amount ${amount} added at ${dateAdded}");
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