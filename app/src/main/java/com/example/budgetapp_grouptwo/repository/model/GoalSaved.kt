package com.example.budgetapp_grouptwo.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/** GoalSaved Entities consists of the expenses related to "saved_amount"-field in Goals.
 *  Foreign keys from the expense and the given goal, the expense went to.
 */
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
