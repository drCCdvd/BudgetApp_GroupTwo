package com.example.budgetapp_grouptwo.model

import android.R
import java.time.*

abstract class Cash (
        val id: Int,
        val name: String,
        val amount: Double,
        val dateAdded: LocalDate
    ) {


    /*
    open fun readOutLoud(): String {
        return ("amount $amount added at $dateAdded");
    }
*/
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