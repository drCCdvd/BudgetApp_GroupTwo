package com.example.mybugdetapp.data

import android.content.Context

object CashFlowStorage {
    private const val PREF_NAME = "budget_prefs"
    private const val KEY_EARNINGS = "regular_earnings"
    private const val KEY_EXPENSES = "regular_expenses"

    fun saveRegularEarnings(context: Context, amount: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(KEY_EARNINGS, amount.toFloat()).apply()
    }

    fun saveRegularExpenses(context: Context, amount: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(KEY_EXPENSES, amount.toFloat()).apply()
    }

    fun loadRegularEarnings(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_EARNINGS, 0f).toDouble()
    }

    fun loadRegularExpenses(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_EXPENSES, 0f).toDouble()
    }
}