package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.ui.utils.CurrencyVisualTransformation



@Composable
fun CreateGoalScreen(
    onSaveGoal: (String, Double) -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Nyt mål",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Hvad sparer du op til?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                // Kun tillad tal
                amount = newValue.filter { it.isDigit() }
            },
            label = { Text("Beløb") },
            visualTransformation = CurrencyVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                if (name.isNotBlank() && parsedAmount != null) {
                    onSaveGoal(name, parsedAmount)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tilføj")
        }
        TextButton(
            onClick = onBackClick
        ) {
            Text("Tilbage")
        }
    }
}