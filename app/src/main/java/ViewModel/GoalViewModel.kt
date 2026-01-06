package com.example.budgetapp_grouptwo.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.Goal

class GoalViewModel : ViewModel() {

    private val _goals = mutableStateListOf<Goal>()
    val goals: List<Goal> = _goals

    fun addGoal(goal: Goal) {
        _goals.add(goal)
    }
}