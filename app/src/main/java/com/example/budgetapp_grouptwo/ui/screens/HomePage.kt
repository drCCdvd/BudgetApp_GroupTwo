package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.components.EditRecurringNavItem
import com.example.budgetapp_grouptwo.ui.components.MainHeader
import com.example.budgetapp_grouptwo.ui.components.QuickActionFabContainer


@Composable
fun HomePage(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Text("HomePage")
        }

        // 🔹 FAB over alt andet
        QuickActionFabContainer(
            navController = navController,
            onCreateGoal = {
                navController.navigate("createGoal")
            }
        )
    }
}