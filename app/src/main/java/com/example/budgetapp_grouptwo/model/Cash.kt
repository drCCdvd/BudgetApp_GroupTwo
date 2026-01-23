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

    /**Temporary function for testing the instantiation of a Cash object
     *
     */
    open fun readOutLoud(): String {
        return ("amount ${amount} added at ${dateAdded}");
    }
}