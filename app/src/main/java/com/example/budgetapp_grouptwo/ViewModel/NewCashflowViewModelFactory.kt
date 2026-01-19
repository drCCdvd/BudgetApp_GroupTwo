package com.example.budgetapp_grouptwo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.model.CashFlow

class NewCashflowViewModelFactory(
    private val cashFlow: CashFlow
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewCashflowViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewCashflowViewModel(cashFlow) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

