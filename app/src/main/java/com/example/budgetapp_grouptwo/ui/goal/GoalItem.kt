package com.example.budgetapp_grouptwo.ui.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.model.Goal
import androidx.compose.material3.OutlinedTextField

@Composable
fun GoalItem(
    goal: Goal,
    onAddMoney: ((Goal, Double) -> Unit)? = null,
    onRemove: ((Int) -> Unit)? = null
) {
    var showConfirm by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var inputAmount by remember { mutableStateOf("") }
    val progress =
        (goal.savedAmount / goal.targetAmount)
            .toFloat()
            .coerceIn(0f, 1f)

    val percent = (progress * 100).toInt()
    val isCompleted = progress >= 1f
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(goal.name, style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(4.dp))
                Text("${percent}% i mål")

                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Spacer(Modifier.height(6.dp))
                Text("${goal.savedAmount.toInt()} / ${goal.targetAmount.toInt()} kr.")
                Text("${goal.endDate}")

                Spacer(Modifier.height(8.dp))
                if (isCompleted) {
                    Text(
                        text = "100% i mål!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    TextButton(
                        onClick = {
                            showAddDialog = true
                        }
                    ) {
                        Text("Tilføj beløb")
                    }
                }
            }

            IconButton(onClick = { showConfirm = true}) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Fjern mål"
                )
            }
        }
    }
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                inputAmount = ""
            },
            title = { Text("Tilføj beløb") },
            text = {
                OutlinedTextField(
                    value = inputAmount,
                    onValueChange = { inputAmount = it.filter { c -> c.isDigit() } },
                    label = { Text("Beløb (kr.)") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val amount = inputAmount.toDoubleOrNull()
                        if (amount != null && amount > 0) {
                            onAddMoney?.invoke(goal, amount)
                        }
                        showAddDialog = false
                        inputAmount = ""
                    }
                ) {
                    Text("Tilføj")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                        inputAmount = ""
                    }
                ) {
                    Text("Annuller")
                }
            }
        )
    }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Fjern mål" ) },
            text = { Text("Vil du slette \"${goal.name}\"?") },
            confirmButton = {
                TextButton(onClick =  {
                showConfirm = false
                onRemove?.invoke(goal.id)
            }) {
                Text("Fjern")
            }
        },
        dismissButton = {
            TextButton(onClick = { showConfirm = false }) {
                Text("Annuller")
            }
        }
      )
    }
}