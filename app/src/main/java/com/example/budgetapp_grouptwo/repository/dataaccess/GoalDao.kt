package com.example.budgetapp_grouptwo.repository.dataaccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetapp_grouptwo.repository.model.Goal

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal ORDER BY endDate DESC")
    suspend fun getAll(): List<Goal>

    @Query("SELECT * FROM goal WHERE uid = :id")
    suspend fun selectById(id: Int): Goal

    @Insert
    suspend fun insertGoal(goal: Goal)

    @Query("DELETE FROM goal WHERE uid = :id")
    suspend fun deleteGoal(id: Int)

    @Update
    suspend fun updateGoal(updatedGoal: Goal)

    @Query("UPDATE Goal SET savedAmount = :newSavedAmount WHERE uid = :id")
    suspend fun insertAmount(id: Int, newSavedAmount: Double)
}