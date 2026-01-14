package com.example.budgetapp_grouptwo

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate

class Converts {
    @TypeConverter
    fun fromString(value: String?): LocalDate?{
        return value?.let{ LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromDate(value: LocalDate): String?{
        return value?.let { it.toString() }
    }
}