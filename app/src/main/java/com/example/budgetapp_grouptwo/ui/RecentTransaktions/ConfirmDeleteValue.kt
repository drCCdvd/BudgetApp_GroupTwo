package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDeleteDialog(
    itemName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val safeName = itemName.ifBlank { "denne post" }     //sikre at item name ikke er tomt

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Fjern post") },
        text = { Text("Vil du slette \"$itemName\"?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Fjern")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuller")
            }
        }
    )
}
