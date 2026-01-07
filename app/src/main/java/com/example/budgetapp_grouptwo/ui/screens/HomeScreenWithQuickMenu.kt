package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.ui.components.QuickActionFab

@Composable
fun HomeScreenWithQuickMenu(
    goals: List<Goal>,
    onCreateGoalClick: () -> Unit,
    onRemoveGoal: (String) -> Unit,
    onAddTransactionClick: () -> Unit,
    onDepositToGoalClick: () -> Unit

) {
    var isQuickMenuOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        HomeScreen(
            goals = goals,
            onCreateGoalClick = onCreateGoalClick,
            onRemoveGoal = onRemoveGoal
        )

        QuickActionFab(
            isOpen = isQuickMenuOpen,
            onToggle = { isQuickMenuOpen = !isQuickMenuOpen },
            onDismiss = { isQuickMenuOpen = false },
            onAddTransaction = { isQuickMenuOpen = false; onAddTransactionClick() },
            onCreateGoal = { isQuickMenuOpen = false; onCreateGoalClick() },
            onDepositToGoal = { isQuickMenuOpen = false; onDepositToGoalClick() }
        )
    }
}