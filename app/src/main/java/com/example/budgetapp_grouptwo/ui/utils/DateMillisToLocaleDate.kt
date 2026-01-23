package com.example.budgetapp_grouptwo.ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Helper for transforming a given Date in milliseconds to a LocaleDate instance
 */
class DateMillisToLocaleDate(
    dateInMillis: Long?
){
    val newDateInMillis = dateInMillis

    fun toLocaleDate(): LocalDate{
        var dateFormatted = LocalDate.now();
        if(newDateInMillis!=null){
            dateFormatted = Instant.ofEpochMilli(newDateInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        }
        return dateFormatted;
    }
}