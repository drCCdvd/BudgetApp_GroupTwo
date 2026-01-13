package com.example.budgetapp_grouptwo.repository.dataaccess

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetapp_grouptwo.repository.model.Goal

interface GoalDao {
    @Query("SELECT * FROM goal")
    fun getAll(): List<Goal>

    @Insert
    fun insertGoal(goal: Goal)

    @Delete
    fun deleteGoal(goal: Goal)

    //Todo implement:
    @Update
    fun updateGoal(goal: Goal)
}