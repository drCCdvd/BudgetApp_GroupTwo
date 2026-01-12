package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.ui.components.BackTopBar



@Composable
fun GoalScreen(
    goals: List<Goal>,
    onCreateGoal: () -> Unit,
    onRemoveGoal: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            BackTopBar(
                title = "Mine mål",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            if (goals.isEmpty()) {
                Text(
                    text = "Ingen mål endnu"
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {
                    items(
                        items = goals,
                        key = { it.id }
                    ) { goal ->
                        GoalItem(
                            goal = goal,
                            onRemove = { id: String ->
                                onRemoveGoal(id)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onCreateGoal,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Opret nyt mål")
            }
        }
    }
}





