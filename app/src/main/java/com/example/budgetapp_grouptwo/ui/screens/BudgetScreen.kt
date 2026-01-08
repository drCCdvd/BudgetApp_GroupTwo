package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.model.RegularCashFlow

@Composable
fun BudgetScreen() {
    // State-variable til at gemme input fra brugeren
    var earnings by remember { mutableStateOf("") }
    var expenses by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text( // overskrift på siden
            text = "Budget Oversigt",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField( // Feltet hvor man skriver indtægter
                value = earnings,
                onValueChange = { earnings = it },
                label = { Text ("Månedlig indkomst") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
                )

        OutlinedTextField( // feltet hvor man skriver udgifter
            value = expenses,
            onValueChange = { expenses = it },
            label = { Text ("Månedlig udgifter") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // UI beregning intil at ViewModel er klar
        val incomeVal = earnings.toDoubleOrNull()?: 0.0
        val expensesVal = expenses.toDoubleOrNull()?: 0.0
        val netCashFlow = incomeVal - expensesVal // netto cashflow

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Rådighedsbeløb:", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = "${String.format("%.2f", netCashFlow)} kr.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (netCashFlow >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
        // Gem knap... vi prøver..
        Button(
            onClick = {
                // Opret en instans af af modellen med de tal brugeren har indtastet
                val myData = RegularCashFlow(
                    earnings = earnings.toDoubleOrNull() ?: 0.0,
                    expenses = expenses.toDoubleOrNull() ?: 0.0,
                    id = "default_user"
                )
                // printer for at teste:
                println("Gemmer data: $myData")
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Gem Indtastninger")
        }
    }
}