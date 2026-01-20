package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // holder afstand mellem alle emnerne
    ) {

        // Sektion: Mål
        item{ // måden at pakke ind på i LazyColumn
            Text(
                text = "Mine mål",
                style = MaterialTheme.typography.titleSmall
            )
        }
        // hvis der mangler spacer så skal den nok være her

        items(recentGoals) { goal ->
            GoalItem(goal = goal)// ingen callbacks → ingen knapper
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }


        // Sektion: Seneste transaktioner
        item {
            Text(
                text = "Seneste transaktioner",
                style = MaterialTheme.typography.titleSmall
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            recentCashFlow
                .sortedByDescending { it.dateAdded }
        ) { cash ->
            CashItem(cash = cash)   // ingen onRemove → ingen knap
            }
    }
}
