package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
//import androidx.glance.appwidget.compose
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.CashFlowStorage
import com.example.budgetapp_grouptwo.RegularCashFlow
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import com.example.budgetapp_grouptwo.ui.theme.Typography
import java.time.LocalDate
import kotlin.math.roundToInt

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.budgetapp_grouptwo.ui.goal.GoalItem

@Composable
fun HomePage(
    navController: NavController,
    cashFlowViewModel: CashFlowViewModel,
    goalViewModel: GoalViewModel,
    //recentCashFlow: List<Cash>,
    //goals: List<Goal>
) {
    val context = LocalContext.current
    var montlyDisposable = cashFlowViewModel.monthlyDisposable;
    var todaysDisposable = cashFlowViewModel.disposableToday;
    var recentCashFlow = cashFlowViewModel.cashFlows.takeLast(5)
    var recentGoals = goalViewModel.goals.takeLast(3);

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(0.dp))

        // Sektion: Mål
        Text(
            text = "Mine mål",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        recentGoals.forEach { goal ->
            GoalItem(goal = goal)// ingen callbacks → ingen knapper
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sektion: Seneste transaktioner
        Text(
            text = "Seneste transaktioner",
            style = MaterialTheme.typography.titleSmall
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
