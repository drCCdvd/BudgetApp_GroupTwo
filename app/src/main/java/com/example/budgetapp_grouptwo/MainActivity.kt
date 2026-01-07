package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.viewmodel.GoalViewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.budgetapp_grouptwo.model.Goal
import com.example.budgetapp_grouptwo.ui.screens.HomeScreen
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen

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
                    startDestination = "home"
                ) {

                    composable("home") {
                        HomeScreen(
                            goals = goalViewModel.goals,
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
                    composable("createGoal") {
                        CreateGoalScreen(
                            onSaveGoal = { name, amount ->
                                goalViewModel.addGoal(name, amount)
                                navController.popBackStack()
                            },
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
