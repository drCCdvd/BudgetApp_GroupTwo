package com.example.budgetapp_grouptwo.repository.dataaccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import com.example.budgetapp_grouptwo.repository.model.CashflowType

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM Cashflow ORDER BY createdDate DESC")
    suspend fun getAll(): List<Cashflow>

/*    @Query("SELECT * FROM Cashflow WHERE type =:_type")
    suspend fun getAllByType(_type: CashflowType)
*/
    @Insert
    suspend fun insertNew(cashflow: Cashflow)

    @Query ("DELETE FROM cashflow where uid = :id")
    suspend fun remove(id: Int)
}