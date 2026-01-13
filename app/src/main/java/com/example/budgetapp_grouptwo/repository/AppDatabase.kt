package com.example.budgetapp_grouptwo.repository

import androidx.room.RoomDatabase
import com.example.budgetapp_grouptwo.repository.dataaccess.GoalDao

abstract class AppDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
}