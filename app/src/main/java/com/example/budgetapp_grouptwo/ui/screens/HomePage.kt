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

import com.example.budgetapp_grouptwo.ui.components.EditRecurringNavItem
import com.example.budgetapp_grouptwo.ui.components.MainHeader
import com.example.budgetapp_grouptwo.ui.components.QuickActionFabContainer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Goal
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
    var goals = goalViewModel.goals;


    LaunchedEffect(Unit) {
        cashFlowViewModel
            .getDisposable(
                LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1),
                LocalDate.now(),
                context
            )
        cashFlowViewModel.getDisposableToday(context);
        montlyDisposable = cashFlowViewModel.monthlyDisposable;
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text ="Til rådighed i dag " + todaysDisposable.value.roundToInt().toString()+",-",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text ="Disponible for ${LocalDate.now().month.name} " + montlyDisposable.value.roundToInt().toString()+",-",
            style = MaterialTheme.typography.bodyMedium
        )
        NavigationMenu(navController)

    }
        Spacer(modifier = Modifier.height(16.dp))

        // Sektion: Mål
        Text(
            text = "Mine mål",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        goals.forEach { goal ->
            GoalItem(goal = goal)// ingen callbacks → ingen knapper
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
