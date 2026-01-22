package com.example.budgetapp_grouptwo.repository

import android.content.Context
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.repository.dataaccess.CashFlowDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalSavedDao
import com.example.budgetapp_grouptwo.repository.model.Cashflow

class GoalRepository(goalDao: GoalDao) {
    private val goalDao = goalDao;

    /** Transform a Goal instance to a Goal Database entity
     * (Id is automatically generated on database insertion)
     */
    fun Goal.toEntity(): com.example.budgetapp_grouptwo.repository.model.Goal{
        return com.example.budgetapp_grouptwo.repository.model.Goal(
            name = name,
            targetAmount = targetAmount,
            savedAmount = savedAmount,
            createdDate = createdDate,
            endDate = endDate,
        )
    }

    /** Transforms a database Goal entity to a Goal instance.
     *
     */
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

    //Fetches all goals
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

    /** Updates "savedAmount" for a given goal
     * Input goalId: the Id of goal to update amount to
     * Input amount: The amount of which to add on to a goal
     */
    suspend fun addMoneyToGoal(goalId: Int, amount: Double){
        var goal = goalDao.selectById(goalId)
        var newAmount = goal.savedAmount+amount
        goalDao.insertAmount(goalId,newAmount);
    }

    suspend fun insertNewGoal(goal: Goal){
        var dbGoal = goal.toEntity();
        goalDao.insertGoal(dbGoal);
    }

    suspend fun deleteGoal(id: Int){
        goalDao.deleteGoal(id);
    }


}