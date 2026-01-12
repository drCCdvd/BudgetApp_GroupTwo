package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Income


@Composable
fun AmountWithButtonRow(
    amount: Double,
    isIncome: Boolean,
    onRemoveClick: () -> Unit
) {
    val color = if (isIncome) Color(0xFF4CAF50) else Color(0xFFF44336)
    val prefix = if (isIncome) "+" else "-"

    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "$prefix${amount},-",
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )

        Spacer(modifier = Modifier.width(30.dp))

        TextButton(onClick = onRemoveClick) {
            Text("Fjern", color = Color.Gray)
        }
    }
}

@Composable
fun RecentItemCard(
    item: Cash,
    onRemoveClick: () -> Unit
) {
    val isIncome = item is Income
    Card(
        modifier = Modifier.fillMaxWidth()
        .wrapContentHeight(),
    elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // LEFT SIDE
            Column {
                Text(
                    text = item.dateAdded.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
            }
            // RIGHT SIDE
            AmountWithButtonRow(
                amount = item.amount,
                isIncome = isIncome,
                onRemoveClick = onRemoveClick
            )
        }

    }
    }


