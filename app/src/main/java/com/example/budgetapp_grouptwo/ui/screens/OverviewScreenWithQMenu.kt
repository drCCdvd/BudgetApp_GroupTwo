package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.budgetapp_grouptwo.ui.components.QuickActionFab

@Composable
fun OverviewScreenWithQMenu(
    onGoalsClick: () -> Unit,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit
) {
    var isQuickMenuOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        //Selve hjemmeskærmen (overblik)
        OverviewScreen()

        //Plus knap og radial menu (kun her)
        QuickActionFab(
            isOpen = isQuickMenuOpen,
            onToggle = { isQuickMenuOpen = !isQuickMenuOpen },
            onDismiss = { isQuickMenuOpen = false },

            //Mapping de tre valg
            onCreateGoal = {
                isQuickMenuOpen = false
                onGoalsClick()
            },
            onAddTransaction = {
                isQuickMenuOpen = false
                onIncomeClick()
            },
            onDepositToGoal = {
                isQuickMenuOpen = false
                onExpenseClick()
            }
        )
    }
}