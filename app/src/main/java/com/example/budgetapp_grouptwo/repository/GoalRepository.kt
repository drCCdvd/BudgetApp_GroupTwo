package com.example.budgetapp_grouptwo.repository

import android.content.Context
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao

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

    fun getAllGoals(): List<Goal>{
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

    fun insertNewGoal(goal: Goal): Goal{
        var dbGoal = goal.toEntity();
        goalDao.insertGoal(dbGoal);
        return dbGoal.toApp();
    }
}