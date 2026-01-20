package com.example.budgetapp_grouptwo.ui.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun GoalItem(
    goal: Goal,
    onAddMoney: ((Goal, Double) -> Unit)? = null,
    onRemove: ((Int) -> Unit)? = null
) {
    var showConfirm by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var inputAmount by remember { mutableStateOf("") }
    val progress = if (goal.targetAmount > 0) {
        (goal.savedAmount / goal.targetAmount)
            .toFloat()
            .coerceIn(0f, 1f)
    } else {
        0f
    }

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
                // Top row: title + percentage in the right corner
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(goal.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "$percent%",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.height(6.dp))
                Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(22.dp)
                ) {
                    val progressColor = when (goal.colorindex % 3) {
                        0 -> Color.Yellow.copy(alpha = 0.4f)   // gul
                        1 -> Color.Blue.copy(alpha = 0.4f)    // blå
                        else -> Color.Green.copy(alpha = 0.4f) // grøn
                    }
                    LinearProgressIndicator(
                        progress = {progress},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(22.dp),
                        color = progressColor,
                        trackColor = Color.LightGray

                    )
                    Text(
                        text = "${goal.savedAmount.toInt()} / ${goal.targetAmount.toInt()} kr.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                }



            }

            }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            if (onRemove != null){
                IconButton(
                    onClick = { /*if (!isCompleted) Så vi kan slette mål, selvom 100%, ændres tilbage senere*/ showConfirm = true },
                ) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Mål fuldført",
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Fjern mål"
                        )
                    }
                }
            }

            if (onAddMoney != null && !isCompleted) {
                TextButton(
                    onClick = { showAddDialog = true }
                ) {
                    Text("Tilføj beløb")
                }
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
                            if (!isCompleted) {
                                val amount = inputAmount.toDoubleOrNull()
                                if (amount != null && amount > 0) {
                                    val remaining = goal.targetAmount - goal.savedAmount
                                    val safeAmount = amount.coerceAtMost(remaining)
                                    if (safeAmount > 0) {
                                        onAddMoney?.invoke(goal, safeAmount)
                                    }
                                }
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
                title = { Text("Fjern mål") },
                text = { Text("Vil du slette \"${goal.name}\"?") },
                confirmButton = {
                    TextButton(onClick = {
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
