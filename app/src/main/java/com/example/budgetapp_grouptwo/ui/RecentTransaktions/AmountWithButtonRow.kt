package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AmountWithButtonRow(
    amount: Double,
    isIncome: Boolean,
    onRemoveClick: () -> Unit
) {
    // ERROR PREVENTION: Sikrer at amount altid er et gyldigt tal
    val safeAmount = amount.takeIf { it.isFinite() } ?: 0.0
    val color = if (isIncome) Color(0xFF4CAF50) else Color(0xFFF44336)
    val prefix = if (isIncome) "+" else "-"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$prefix${safeAmount},-",   // <-- EP: bruger safeAmount
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )

        Spacer(modifier = Modifier.width(30.dp))

        TextButton(onClick = onRemoveClick) {
            Text("Fjern", color = Color.Gray)
        }
    }
}
