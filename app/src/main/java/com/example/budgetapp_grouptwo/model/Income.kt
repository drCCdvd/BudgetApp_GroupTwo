package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Income(
        amount: Int,
        date: LocalDate
    ): Cash(amount, date){
}