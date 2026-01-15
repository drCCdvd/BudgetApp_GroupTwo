package com.example.budgetapp_grouptwo.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.repository.CashFlowRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CashFlowViewModel(cashFlowRepository: CashFlowRepository): ViewModel() {

    var repository = cashFlowRepository;
    var cashFlow = CashFlow();
    //var monthlyDisposable = mutableStateOf<Double>(0.0)
    //var dailyDisposable = mutableStateOf<Double>(0.0);
    var cashFlows = cashFlow.cashFlows

    var monthlyDisposable = 0.0;

    //Load cashflow initially
    init {
        viewModelScope.launch {
            fetchAllCashFlows();
        }
    }

    fun addExpense(expense: Expense) = viewModelScope.launch {
        var new_expense = repository.addNewExpense(expense)
        cashFlows.add(new_expense);
    }

    fun addIncome(income: Income) = viewModelScope.launch {
        var new_income = repository.addNewIncome(income);
        cashFlows.add(new_income);
    }

    fun fetchAllCashFlows() = viewModelScope.launch {
        var cashFlowData  = repository.getAllCashFlows();
        cashFlows.clear()
        cashFlows.addAll(cashFlowData);
    }

    fun removeIncome(id: Int) = viewModelScope.launch{
        println("slet" + id)
        repository.removeIncome(id);
        fetchAllCashFlows();
    }

    fun removeExpense(id: Int) = viewModelScope.launch{
        println("slet" + id)
        repository.removeExpense(id);
        fetchAllCashFlows();
    }

    /*fun fetchRegularCashFlow() = viewModelScope.launch {
        //Todo add
    }

    fun editRegularCashFlow() = viewModelScope.launch {
        //Todo add
    }*/

    fun getDisposable(startDate: LocalDate, endDate: LocalDate, context: Context) = viewModelScope.launch{
        fetchAllCashFlows()
        monthlyDisposable = cashFlow.getDisposable(startDate, endDate, context);
    }

}

class CashFlowViewModelFactory(private val repository: CashFlowRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CashFlowViewModel::class.java)){
            return CashFlowViewModel(repository) as T;
        }
        throw IllegalArgumentException("Unknown class");
    }
}