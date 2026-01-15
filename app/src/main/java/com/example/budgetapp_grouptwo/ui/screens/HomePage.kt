package com.example.budgetapp_grouptwo.ui.screens

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


@Composable
fun HomePage(
    navController: NavController,
    cashFlowViewModel: CashFlowViewModel,
    goalViewModel: GoalViewModel,
) {
    val context = LocalContext.current
    var montlyDisposable = cashFlowViewModel.monthlyDisposable;
    var todaysDisposable = cashFlowViewModel.disposableToday;

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
}
