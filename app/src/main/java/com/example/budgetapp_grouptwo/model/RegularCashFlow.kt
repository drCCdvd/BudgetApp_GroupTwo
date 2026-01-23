package com.example.budgetapp_grouptwo

/**
 * This object is for managing the regular cash flow
 * (Cash flow storage is for loading and saving the data to sharedPreferences)
 */
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