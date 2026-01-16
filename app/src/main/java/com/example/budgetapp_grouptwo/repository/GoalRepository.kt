package com.example.budgetapp_grouptwo.repository

import android.content.Context
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.repository.dataaccess.CashFlowDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao
import com.example.budgetapp_grouptwo.repository.model.Cashflow

class GoalRepository(goalDao: GoalDao) {
    private val goalDao = goalDao;

    fun Goal.toEntity(): com.example.budgetapp_grouptwo.repository.model.Goal{
        return com.example.budgetapp_grouptwo.repository.model.Goal(
            name = name,
            targetAmount = targetAmount,
            savedAmount = savedAmount,
            createdDate = createdDate,
            endDate = endDate,
        )
    }

    fun com.example.budgetapp_grouptwo.repository.model.Goal.toApp(): Goal{
        return Goal(
            id = uid,
            name = name,
            targetAmount = targetAmount,
            savedAmount = savedAmount,
            createdDate = createdDate,
            endDate = endDate
        )
    }

    suspend fun getAllGoals(): List<Goal>{
        var goalsFromDAO = goalDao.getAll();

        var goals = goalsFromDAO.map { dbGoal ->
            Goal(
                id = dbGoal.uid,
                name = dbGoal.name,
                targetAmount = dbGoal.targetAmount,
                savedAmount = dbGoal.savedAmount,
                createdDate = dbGoal.createdDate,
                endDate = dbGoal.endDate,
            )
        }
        return goals;
    }

    suspend fun addMoneyToGoal(goalId: Int, amount: Double){
        var goal = goalDao.selectById(goalId)
        var newAmount = goal.savedAmount+amount
        goalDao.insertAmount(goalId,newAmount);
    }

    suspend fun insertNewGoal(goal: Goal): Goal{
        var dbGoal = goal.toEntity();
        goalDao.insertGoal(dbGoal);
        return dbGoal.toApp();
    }

    suspend fun deleteGoal(id: Int){
        goalDao.deleteGoal(id);
    }


}