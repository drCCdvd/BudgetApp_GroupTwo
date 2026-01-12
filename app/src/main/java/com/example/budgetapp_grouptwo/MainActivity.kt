package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import androidx.navigation.compose.composable
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashFlowContent
import com.example.budgetapp_grouptwo.ui.screens.RecentPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var cashFlow: CashFlow = CashFlow();
        var newCashflowViewModel = NewCashflowViewModel(cashFlow);

        enableEdgeToEdge()

        setContent {
            BudgetApp_GroupTwoTheme {
                val navController = rememberNavController()
                val goalViewModel: GoalViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    // HOME PAGE
                    composable("home") {
                        HomePage(navController = navController)
                    }
                    // RECENT PAGE
                    composable("recent") {
                        RecentPage(navController = navController)
                    }
                    // Goal page
                    composable("goals") {
                        GoalsPage(
                            goals = goalViewModel.goals,
                            navController = navController,
                            onCreateGoalClick = {
                                navController.navigate("createGoal")
                            },
                            onAddMoney = { id, amount ->
                                goalViewModel.addMoney(id, amount)
                            },
                            onRemoveGoal = { id ->
                                goalViewModel.removeGoal(id)
                            }
                        )
                    }

                    // CREATE GOAL Page
                    composable("createGoal") {
                        CreateGoalScreen(
                            navController = navController,
                            onSaveGoal = { name, amount ->
                                goalViewModel.addGoal(name, amount)
                                navController.popBackStack()
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // insert new cashflow
                    composable("insertNewCashflow") {
                        InsertNewCashFlowContent(
                            onBack = {navController.popBackStack()},
                            newCashflowViewModel
                        )
                    }

                    // EDIT REGULAR CASHFLOW
                    composable("editRegularCashflow") {
                        FixedEntryScreen (onBack = {navController.popBackStack()})
                    }
                }
            }
        }
    }
}