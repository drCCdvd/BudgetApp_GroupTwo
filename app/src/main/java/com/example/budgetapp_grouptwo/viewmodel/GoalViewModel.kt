package com.example.budgetapp_grouptwo.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.Goal
import java.util.UUID

class GoalViewModel : ViewModel() {

    private val _goals = mutableStateListOf<Goal>()
    val goals: List<Goal> = _goals

    fun addGoal(name: String, amount: Double) {
        _goals.add(
            Goal(
                id = UUID.randomUUID().toString(),
                name = name,
                targetAmount = amount,
                savedAmount = 0.0
            )
        )
    }
    fun removeGoal(goalId: String) {
        //for at state ændres korrekt
        _goals.removeAll {it.id == goalId}
    }
    fun addMoney(goalId: String, amount: Double) {
        val index = _goals.indexOfFirst { it.id == goalId }
        if (index != -1) {
            val goal = _goals[index]

            val remaining = goal.targetAmount - goal.savedAmount
            val safeAmount = minOf(amount, remaining)

            if (safeAmount <= 0) return

            _goals[index] = goal.copy(
                savedAmount = goal.savedAmount + safeAmount
            )
        }
    }
}