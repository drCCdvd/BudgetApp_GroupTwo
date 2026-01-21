package com.example.budgetapp_grouptwo.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Cashflow::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("cashflowUID"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Goal::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("goalUID"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoalSaved (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("cashflowUID")
    val cashflowUID: Int,
    @ColumnInfo("goalUID")
    val goalUID: Int,
)
