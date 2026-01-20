package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.screens.NavigationMenu

@Composable
fun PageLayout(
    navController: NavController,
    title: String,
    subtitle: String? = null,
    showEditRecurring: Boolean = false,
    onEditRecurringClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // 🔹 HEADER
        MainHeader(
            title = title,
            subtitle = subtitle,
            showEditRecurring = showEditRecurring,
            onEditRecurringClick = onEditRecurringClick
        )

        // 🔹 NAVIGATION – altid samme sted
        NavigationMenu(navController)

        // 🔹 SIDEINDHOLD
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            content()
        }
    }
    QuickActionFabContainer(navController = navController)
}