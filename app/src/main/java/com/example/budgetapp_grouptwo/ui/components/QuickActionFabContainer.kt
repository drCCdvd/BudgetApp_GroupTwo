package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier


/** A countainer that wraps the QuickActionFab
 * (to ensure a consistent functionality across screens and pages)
 */
@Composable
fun QuickActionFabContainer(
    navController: NavController,
    ) {
    var fabOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        QuickActionFab(
            isOpen = fabOpen,
            onToggle = { fabOpen = !fabOpen },
            onDismiss = { fabOpen = false },

            onCreateGoal = {
                fabOpen = false
                navController.navigate("createGoal")
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
}