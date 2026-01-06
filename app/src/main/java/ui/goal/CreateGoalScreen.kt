package com.example.budgetapp_grouptwo.ui.goal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateGoalScreen(
    onSave: (String, Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(24.dp)) {
        Text("Nyt mål")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Hvad sparer du op til?") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            placeholder = { Text("Beløb") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            onSave(name, amount.toDouble())
        }) {
            Text("Tilføj")
        }
    }
}