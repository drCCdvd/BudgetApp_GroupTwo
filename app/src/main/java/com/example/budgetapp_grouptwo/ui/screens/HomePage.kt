package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.model.Cash


@Composable
fun HomePage(
    navController: NavController,
    recentCashFlow: List<Cash>   // ← tilføjet
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        NavigationMenu(navController)

        Text("HomePage")

        // Vis de seneste poster uden sletteknap
        recentCashFlow.forEach { cash ->
            CashItem(cash = cash)   // ← ingen onRemove → ingen knap
        }
    }
}

