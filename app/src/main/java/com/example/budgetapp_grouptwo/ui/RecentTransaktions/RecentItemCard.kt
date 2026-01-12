package com.example.budgetapp_grouptwo.ui.RecentTransaktions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.components.ConfirmDeleteDialog
import com.example.budgetapp_grouptwo.ui.components.AmountWithButtonRow




@Composable
fun RecentItemCard(
    item: Cash,
    onRemoveClick: () -> Unit
) {
    val isIncome = item is Income
    var showConfirm by remember { mutableStateOf(false) }

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
                onRemoveClick = { showConfirm = true } // <-- ÅBN DIALOG
            )
        }

    }

    if (showConfirm) {
        ConfirmDeleteDialog(
            itemName = item.name,
            onConfirm = {
                showConfirm = false
                onRemoveClick()
            },
            onDismiss = { showConfirm = false }
        )
    }
}




