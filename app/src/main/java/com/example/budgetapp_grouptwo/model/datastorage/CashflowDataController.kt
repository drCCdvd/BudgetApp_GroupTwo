package com.example.budgetapp_grouptwo.model.datastorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.budgetapp_grouptwo.model.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CashflowDataController(private val context: Context) {

    fun loadGoals(): Flow<List<Goal>> = context.budgetAppUserData.data.map { CashflowDataSettings -> CashflowDataSettings.goals }
    suspend fun saveGoals(goals: List<Goal>){
        context.budgetAppUserData.updateData {
            CashflowDataSettings -> CashflowDataSettings.copy(goals=goals)
        }
    }
}