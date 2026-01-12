package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ui.components.BackTopBar
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalScreen
import com.example.budgetapp_grouptwo.ui.screens.OverviewScreenWithQMenu
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.viewmodel.GoalViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BudgetApp_GroupTwoTheme {
                val navController = rememberNavController()
                val goalViewModel: GoalViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "overview"
                ) {

                    composable("overview") {
                        OverviewScreenWithQMenu(
                            onGoalsClick = { navController.navigate("goals") },
                            onIncomeClick = { navController.navigate("income") },
                            onExpenseClick = { navController.navigate("expense") }
                        )
                    }

                    composable("goals") {
                        GoalScreen(
                            goals = goalViewModel.goals,
                            onCreateGoal = { navController.navigate("createGoal") },
                            onRemoveGoal = { id -> goalViewModel.removeGoal(id) },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable("createGoal") {
                        Scaffold(
                            topBar = {
                                BackTopBar(
                                    title = "Opret mål",
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        ) { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                CreateGoalScreen(
                                    onSaveGoal = { name, amount ->
                                        goalViewModel.addGoal(name, amount)
                                        navController.popBackStack()
                                    },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }

                    // ✅ Indtægt med synlig tilbage-knap
                    composable("income") {
                        Scaffold(
                            topBar = {
                                BackTopBar(
                                    title = "Indtægt",
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        ) { padding ->
                            Text(
                                "Indtægt (TODO)",
                                modifier = Modifier.padding(padding).padding(16.dp)
                            )
                        }
                    }

                    // ✅ Udgift med synlig tilbage-knap
                    composable("expense") {
                        Scaffold(
                            topBar = {
                                BackTopBar(
                                    title = "Udgift",
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        ) { padding ->
                            Text(
                                "Udgift (TODO)",
                                modifier = Modifier.padding(padding).padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
