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


@Composable
fun HomePage(
    navController: NavController,
    cashFlowViewModel: CashFlowViewModel,
    goalViewModel: GoalViewModel,
) {
    val context = LocalContext.current
    var montlyDisposable by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        cashFlowViewModel
            .getDisposable(
                LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1),
                LocalDate.now(),
                context
            )
        montlyDisposable = cashFlowViewModel.monthlyDisposable;
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text ="Til rådighed: " + montlyDisposable.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
    NavigationMenu(navController) //
    Text("homPage")
        //add more a buttom for my fage
        androidx.compose.material3.Button(
            onClick = { navController.navigate("editRegularCashflow") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Gå til Fast (Fixed Expenses)")
        }
}
}
// tao nut ket noi cac trang

