package com.example.budgetapp_grouptwo.repository.dataaccess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalSavedDao {
    @Query("Insert into GoalSaved(goalUID, cashflowUID) VALUES(:goalId, :cashFlowId)")
    suspend fun insertNewSavedAmount(goalId: Int, cashFlowId: Long)
}