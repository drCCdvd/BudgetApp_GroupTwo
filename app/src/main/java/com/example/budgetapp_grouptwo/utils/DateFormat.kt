package com.example.budgetapp_grouptwo.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

class DateFormat {
    fun fromMillisToDate(millis: Long): LocalDate{
        //val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        //return formatter.format(Date(millis))
        return LocalDate.now();
    }
}