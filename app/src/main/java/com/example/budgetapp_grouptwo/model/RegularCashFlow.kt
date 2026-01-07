package com.example.mybugdetapp.data

object RegularCashFlow {
    private var regularEarnings: Double = 0.0
    private var regularExpenses: Double = 0.0

    fun getRegularEarnings(): Double = regularEarnings
    fun getRegularExpenses(): Double = regularExpenses

    fun setRegularEarnings(amount: Double) {
        regularEarnings = amount
    }

    fun setRegularExpense(amount: Double) {
        regularExpenses = amount
    }
}