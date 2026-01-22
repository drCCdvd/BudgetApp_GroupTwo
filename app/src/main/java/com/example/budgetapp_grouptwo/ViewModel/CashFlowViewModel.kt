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
import com.example.budgetapp_grouptwo.repository.GoalSavedRepository
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

/** Handling all cashflow logic between UI states and the database
 */
class CashFlowViewModel(cashFlowRepository: CashFlowRepository): ViewModel() {
    var repository = cashFlowRepository;
    var cashFlow = CashFlow();
    var cashFlows = cashFlow.cashFlows
    var monthlyDisposable = mutableStateOf(0.0);
    var dailyDisposable = mutableStateOf(0.0);
    var disposableToday = mutableStateOf(0.0);

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

    /** Inserts new Expense on database and creates relationsship to Goal
     * (Used for adding new saved amounts on a goal)
     */
    fun insertCashFlowAndLinkToGoal(expense: Expense, goalId: Int) = viewModelScope.launch{
        repository.insertCashFlowAndLinkToGoal(expense,goalId);
    }

    fun addIncome(income: Income) = viewModelScope.launch {
        var new_income = repository.addNewIncome(income);
        fetchAllCashFlows();
    }

    suspend fun fetchAllCashFlows() {
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
        repository.removeExpense(id);
        fetchAllCashFlows();
    }

    /** Deletes all Expenses related to a given goal (through GoalSaved table)
     * (Used, when deleting a goal, to release the expenses back in the budget)
     */
    fun removeAllSavedAmount(goalId: Int) = viewModelScope.launch {
        repository.removeAllSavedExpenses(goalId);
        fetchAllCashFlows();
    }

    /** Calculate the disposable from a given startDate and endDate
     */
    fun getDisposable(startDate: LocalDate, endDate: LocalDate, context: Context) = viewModelScope.launch{
        fetchAllCashFlows()
        monthlyDisposable.value = cashFlow.getDisposable(startDate, endDate, context);
    }

    /** Calculates the disposable left for today,
     * based on the monthly disposable.
     */
    fun getDisposableToday(currentDate: LocalDate, context: Context) = viewModelScope.launch {
        fetchAllCashFlows()

        //Calculate the regular cashflow spread across each day:
        dailyDisposable.value = cashFlow.getRegularDisposable(
            currentDate,
            currentDate,
            context
        )/ currentDate.lengthOfMonth()

        println(dailyDisposable.value)

        //Calculate variable cashflow for today:
        var todaysAccounting = 0.0;
        for(cash in cashFlow.cashFlows){
            if(cash.dateAdded!= currentDate){
                continue;
            }

            if(cash is Expense){
                todaysAccounting-=cash.amount;
            }else{
                todaysAccounting+=cash.amount;
            }
        }

        //Adjust disposable today, to include variable changes for today:
        disposableToday.value = dailyDisposable.value+todaysAccounting;
    }
}

/** Uses Factory method from viewmodelProvider to instantiate the viewModel with input for repository
 */
class CashFlowViewModelFactory(private val repository: CashFlowRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CashFlowViewModel::class.java)){
            return CashFlowViewModel(repository) as T;
        }
        throw IllegalArgumentException("Unknown class");
    }
}