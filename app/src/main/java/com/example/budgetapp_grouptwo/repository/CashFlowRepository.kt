package com.example.budgetapp_grouptwo.repository

import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.repository.dataaccess.CashFlowDao
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import com.example.budgetapp_grouptwo.repository.model.CashflowType
import kotlin.math.exp

class CashFlowRepository(cashFlowDao: CashFlowDao) {
    private val cashFlowDao = cashFlowDao;

    fun Income.toEntity(): Cashflow{
        return Cashflow(
            name = name,
            amount = amount,
            createdDate = dateAdded,
            type = CashflowType.Earning
        )
    }
    fun Expense.toEntity(): Cashflow{
        return Cashflow(
            name=name,
            amount = amount,
            createdDate = dateAdded,
            type = CashflowType.Expense
        )
    }

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

    suspend fun addNewExpense(expense: Expense): Cash{
        var newCashFlow = expense.toEntity();
        cashFlowDao.insertNew(newCashFlow);
        return newCashFlow.toApp();
    }

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
}