package com.example.budgetapp_grouptwo.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Income {
    @PrimaryKey(autoGenerate = true) val uid: Int=0,

}