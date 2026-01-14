package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.ui.goal.GoalItem
import androidx.compose.runtime.*
import com.example.budgetapp_grouptwo.ui.components.MainHeader
import com.example.budgetapp_grouptwo.ui.components.QuickActionFab
import com.example.budgetapp_grouptwo.ui.components.QuickActionFabContainer

@Composable
fun GoalsPage(
    goals: List<Goal>,
    onCreateGoalClick: () -> Unit,
    navController: NavController,
    onAddMoney: (String, Double) -> Unit,
    onRemoveGoal: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Mine mål",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (goals.isEmpty()) {
                Text("Ingen mål endnu")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = goals, key = { it.id }) { goal ->
                        GoalItem(
                            goal = goal,
                            onAddMoney = onAddMoney,
                            onRemove = onRemoveGoal
                        )
                    }
                }
            }
        }

        QuickActionFabContainer(
            navController = navController,
            onCreateGoal = onCreateGoalClick
        )
    }
}