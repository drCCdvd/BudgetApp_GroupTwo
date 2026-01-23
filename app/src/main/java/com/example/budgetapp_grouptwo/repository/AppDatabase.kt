package com.example.budgetapp_grouptwo.repository

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetapp_grouptwo.Converters
import com.example.budgetapp_grouptwo.repository.dataaccess.CashFlowDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalSavedDao
import com.example.budgetapp_grouptwo.repository.model.Goal
import com.example.budgetapp_grouptwo.repository.model.Cashflow
import com.example.budgetapp_grouptwo.repository.model.GoalSaved

@Database(
    entities = [
        Goal::class,
        Cashflow::class,
        GoalSaved::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from=1,to=2),
        AutoMigration(from=2,to=3),
        AutoMigration(from=3,to=4)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun cashFlowDao(): CashFlowDao
    abstract fun goalSavedDao(): GoalSavedDao
}