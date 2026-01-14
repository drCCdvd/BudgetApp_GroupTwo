package com.example.budgetapp_grouptwo.ViewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.repository.CashFlowRepository
import com.example.budgetapp_grouptwo.repository.DatabaseProvider
import com.example.budgetapp_grouptwo.repository.GoalRepository
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class GoalViewModel(goalRepository: GoalRepository) : ViewModel() {

    private var _goals = mutableStateListOf<Goal>()
    var goals: List<Goal> = _goals


    private val repository: GoalRepository = goalRepository;

    //Load goals from room database on initialization
    init {
        viewModelScope.launch {
            fetchGoals();
        }
    }

    fun addGoal(name: String, amount: Double, endDate: LocalDate) = viewModelScope.launch {
        var newGoal = Goal(
            name = name,
            targetAmount = amount,
            savedAmount = 0.0,
            createdDate = LocalDate.now(),
            endDate = endDate,
        )
        var insertedGoal = repository.insertNewGoal(newGoal);
        _goals.add(insertedGoal);

    }
    fun removeGoal(goalId: Int) = viewModelScope.launch{
        repository.deleteGoal(goalId)
        fetchGoals();
    }
    fun addMoney(goal: Goal, amount: Double) = viewModelScope.launch{
        repository.addMoneyToGoal(goal.id, amount);
        fetchGoals();
    }

    fun fetchGoals() = viewModelScope.launch{
        val initialized_goals: List<Goal> = repository.getAllGoals();
        _goals.clear()
        _goals.addAll(initialized_goals)
    }
}

class GoalViewModelFactory(private val repository: GoalRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GoalViewModel::class.java)){
            return GoalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class");
    }
}