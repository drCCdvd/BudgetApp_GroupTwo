package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun QuickActionFabContainer(
    navController: NavController,
    onCreateGoal: () -> Unit
) {
    var fabOpen by remember { mutableStateOf(false) }

    QuickActionFab(
        isOpen = fabOpen,
        onToggle = { fabOpen = !fabOpen },
        onDismiss = { fabOpen = false },

        onCreateGoal = {
            fabOpen = false
            onCreateGoal()
        },

        onAddTransaction = {
            fabOpen = false
            navController.navigate("insertNewCashflow")
        },

        onDepositToGoal = {
            fabOpen = false
        }
    )
}