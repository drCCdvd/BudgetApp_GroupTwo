package com.example.budgetapp_grouptwo.ViewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.model.datastorage.CashflowDataController
import com.example.budgetapp_grouptwo.repository.DatabaseProvider
import com.example.budgetapp_grouptwo.repository.GoalRepository
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class GoalViewModel(goalRepository: GoalRepository) : ViewModel() {

    private val _goals = mutableStateListOf<Goal>()
    val goals: List<Goal> = _goals

    private val repository: GoalRepository = goalRepository;

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
    fun removeGoal(goalId: String) {
        //for at state ændres korrekt
        //_goals.removeAll {it.id == goalId}
    }
    fun addMoney(goalId: String, amount: Double) {
        /*val index = _goals.indexOfFirst { it.id == goalId }
        if (index != -1) {
            val goal = _goals[index]

            val remaining = goal.targetAmount - goal.savedAmount
            val safeAmount = minOf(amount, remaining)

            if (safeAmount <= 0) return

            _goals[index] = goal.copy(
                savedAmount = goal.savedAmount + safeAmount
            )
        }*/
    }

    fun loadGoals(){

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