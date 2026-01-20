
package com.example.budgetapp_grouptwo

import com.example.budgetapp_grouptwo.model.Goal
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class ExampleUnitTest {

    @Test
    fun goal_savedAmount_increases_correctly() {
        val goal = Goal(
            id = 1,
            name = "Car",
            targetAmount = 1000.0,
            savedAmount = 200.0,
            createdDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(30)
        )

        val updatedGoal = goal.copy(
            savedAmount = goal.savedAmount + 300.0
        )

        assertEquals(500.0, updatedGoal.savedAmount, 0.0)
    }

    @Test
    fun goal_is_completed_when_savedAmount_reaches_target() {
        val goal = Goal(
            id = 2,
            name = "Phone",
            targetAmount = 500.0,
            savedAmount = 500.0,
            createdDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(10)
        )

        val isCompleted = goal.savedAmount >= goal.targetAmount

        assertEquals(true, isCompleted)
    }
}
