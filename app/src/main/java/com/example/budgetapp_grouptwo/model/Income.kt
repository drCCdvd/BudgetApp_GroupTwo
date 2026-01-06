package com.example.budgetapp_grouptwo.model

import java.time.LocalDate

class Earning(
        amount: Int,
        date: LocalDate
    ): Cash(amount, date){
}