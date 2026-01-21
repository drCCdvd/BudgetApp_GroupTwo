package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import com.example.budgetapp_grouptwo.ui.components.LabelTitle
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
        verticalArrangement = Arrangement.spacedBy(8.dp) // holder afstand mellem alle emnerne
    ) {

        // Sektion: Mål
        item{
            Box(
                modifier = Modifier
                    .clickable{navController.navigate("goals")}
            ) {
                LabelTitle(title = "Mine mål", imageVector = Icons.Filled.Edit);
            }
        }

        items(recentGoals.sortedBy { it.endDate }) { goal ->
            GoalItem(goal = goal)// ingen callbacks → ingen knapper
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }


        // Sektion: Seneste transaktioner

        item {
            Box(
                modifier = Modifier
                    .clickable{navController.navigate("recentDetails")}
            ) {
            LabelTitle(title = "Seneste transaktioner", imageVector = Icons.Rounded.MoreHoriz);
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            recentCashFlow
                .sortedByDescending { it.dateAdded }
        ) { cash ->
            CashItem(cash = cash)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                color = Color.LightGray.copy(alpha = 0.4f),
                thickness = 1.dp
            )

        }

    }
}
