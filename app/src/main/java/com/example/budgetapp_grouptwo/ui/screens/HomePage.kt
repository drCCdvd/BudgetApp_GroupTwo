package com.example.budgetapp_grouptwo.ui.screens

import android.R.attr.fontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.ui.goal.GoalItem

@Composable
fun HomePage(
    navController: NavController,
    recentCashFlow: List<Cash>,
    goals: List<Goal>          // ← NYT: mål sendes ind udefra
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        NavigationMenu(navController)

        // Overskrift
        Text(
            text = "HomePage",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sektion: Mål
        Text(
            text = "Mine mål",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        goals.forEach { goal ->
            GoalItem(goal = goal)   // ingen callbacks → ingen knapper
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sektion: Seneste transaktioner
        Text(
            text = "Seneste transaktioner",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        recentCashFlow
            .sortedByDescending { it.dateAdded }
            .forEach { cash ->
                CashItem(cash = cash)   // ingen onRemove → ingen knap
                Spacer(modifier = Modifier.height(12.dp))
            }
    }
}








