package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel

@Composable
fun RecentPage(
    navController: NavController,
    cashFlowViewModel: CashFlowViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp) // or whatever your shell expects
           
    ) {
        Text(modifier = Modifier.padding(horizontal = 10.dp), text = "Recent Page", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        DetailsContent(
            cashFlow = cashFlowViewModel.cashFlows,
            navController = navController,
            onRemoveIncome = { id ->
                cashFlowViewModel.removeIncome(id)
            },
            onRemoveExpense = { id ->
                cashFlowViewModel.removeExpense(id)
            }
        )
    }
}




