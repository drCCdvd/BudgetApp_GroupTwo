package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.model.Goal

@Composable
fun HomeScreen(
    goals: List<Goal>,
    onCreateGoalClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Mine mål",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (goals.isEmpty()) {
            Text("Ingen mål endnu")
        } else {
            goals.forEach { goal ->
                Text(
                    text = "${goal.name} – ${goal.targetAmount} kr."
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onCreateGoalClick) {
            Text("Opret nyt mål")
        }
    }
}