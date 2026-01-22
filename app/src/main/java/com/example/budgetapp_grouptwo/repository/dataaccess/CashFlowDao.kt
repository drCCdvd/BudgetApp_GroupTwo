package com.example.budgetapp_grouptwo.repository.dataaccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import com.example.budgetapp_grouptwo.repository.model.CashflowType

/** Cashflow data access object
 * Handles data operations for cashflow entities from room DB
 */
@Dao
interface CashFlowDao {
    @Query("SELECT * FROM Cashflow ORDER BY createdDate DESC")
    suspend fun getAll(): List<Cashflow>

/*    @Query("SELECT * FROM Cashflow WHERE type =:_type")
    suspend fun getAllByType(_type: CashflowType)
*/
    @Insert
    suspend fun insertNew(cashflow: Cashflow): Long

    @Query ("DELETE FROM cashflow where uid = :id")
    suspend fun remove(id: Int)

    @Query("DELETE FROM Cashflow WHERE uid IN (SELECT cashflowUID FROM GoalSaved WHERE goalUID = :goalId)")
    suspend fun deleteCashflowsForGoal(goalId: Int)
}