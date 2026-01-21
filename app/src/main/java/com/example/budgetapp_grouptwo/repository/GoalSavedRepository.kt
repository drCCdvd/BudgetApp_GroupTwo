package com.example.budgetapp_grouptwo.repository

import com.example.budgetapp_grouptwo.repository.dataaccess.GoalSavedDao

class GoalSavedRepository(goalSavedDao: GoalSavedDao) {
    private val goalSavedDao = goalSavedDao;

    suspend fun insertNewSavedAmount(goalId: Int, cashflowId: Long){
        goalSavedDao.insertNewSavedAmount(goalId, cashflowId);
    }
}