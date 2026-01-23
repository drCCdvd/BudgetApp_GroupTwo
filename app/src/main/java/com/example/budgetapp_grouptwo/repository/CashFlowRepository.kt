package com.example.budgetapp_grouptwo.repository

import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.repository.dataaccess.CashFlowDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalSavedDao
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import com.example.budgetapp_grouptwo.repository.model.CashflowType
import kotlin.math.exp

class CashFlowRepository(cashFlowDao: CashFlowDao, goalSavedDao: GoalSavedDao) {
    private val cashFlowDao = cashFlowDao;
    private val goalSavedDao = goalSavedDao;

    /** Transforms Income to database entity
     * Maps the App's Income class to a database Cashflow class
     */
    fun Income.toEntity(): Cashflow{
        return Cashflow(
            name = name,
            amount = amount,
            createdDate = dateAdded,
            type = CashflowType.Earning
        )
    }

    /** Transforms expense to database entity
     * Maps the App's expense class to a database Cashflow class
     */
    fun Expense.toEntity(): Cashflow{
        return Cashflow(
            name=name,
            amount = amount,
            createdDate = dateAdded,
            type = CashflowType.Expense
        )
    }

    /** Transforms Cashflow to Income or Expense object
     * Maps the Database Cashflow entity to a new Income or Expense object
     */
    fun Cashflow.toApp(): Cash{
        when(type){
            CashflowType.Earning -> return Income(
                id = uid,
                name=name,
                amount = amount,
                date = createdDate,
            )

            CashflowType.Expense -> return Expense(
                id = uid,
                name=name,
                amount=amount,
                type= ExpenseType.RegularExpense,
                date = createdDate,
            )
        }
    }

    /** Fetches all cashflows from room DB
     *
     */
    suspend fun getAllCashFlows(): List<Cash>{
        var cashFlows = cashFlowDao.getAll()

        return cashFlows.map { cashflow ->
            if(cashflow.type == CashflowType.Expense){
                Expense(
                    id = cashflow.uid,
                    name = cashflow.name,
                    amount = cashflow.amount,
                    date = cashflow.createdDate,
                    type = ExpenseType.RegularExpense,
                )
            }else{
                Income(
                    id = cashflow.uid,
                    name = cashflow.name,
                    amount = cashflow.amount,
                    date = cashflow.createdDate,
                )
            }
        }
    }

    /** Inserts a new cashflow (type: expense) on the database
     * The Expense input is the new instance of expense
     */
    suspend fun addNewExpense(expense: Expense): Cash{
        var newCashFlow = expense.toEntity();
        cashFlowDao.insertNew(newCashFlow);
        return newCashFlow.toApp();
    }

    /** Inserts new Expense on the database and links to a goal using GoalSaved table
     * The Expense input: the instance of an expense
     * The GoalId input: the id of the goal, which the expense is related to
     */
    suspend fun insertCashFlowAndLinkToGoal(expense: Expense, goalId: Int){
        var newCashFlow = expense.toEntity();
        var newCashFlowId = cashFlowDao.insertNew(newCashFlow)
        goalSavedDao.insertNewSavedAmount(goalId, newCashFlowId);
    }

    /** Inserts a new cashflow (type: expense) on the database
     * The Expense input is the new instance of expense
     */
    suspend fun addNewIncome(income: Income): Cash{
        var newCashFlow = income.toEntity();
        cashFlowDao.insertNew(newCashFlow);
        return newCashFlow.toApp()
    }

    suspend fun removeIncome(id: Int){
        cashFlowDao.remove(id);
    }

    suspend fun removeExpense(id: Int){
        cashFlowDao.remove(id);
    }

    suspend fun removeAllSavedExpenses(goalId: Int){
        cashFlowDao.deleteCashflowsForGoal(goalId);
    }
}