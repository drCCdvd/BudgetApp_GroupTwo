package com.example.budgetapp_grouptwo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashFlowContent
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashflowScreen
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import java.time.LocalDate
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

        var newCashflowViewModel = NewCashflowViewModel();

        enableEdgeToEdge()

        setContent {
            BudgetApp_GroupTwoTheme {
                val goalViewModel: GoalViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // 1. Thêm innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) { // 2. Bọc Box để tránh bị che bởi thanh trạng thái

                        CreateGoalScreen(
                            onSaveGoal = { name, amount ->
                                goalViewModel.addGoal(name, amount)
                                //navController.popBackStack()
                            },
                            onBackClick = {
                                //navController.popBackStack()
                            }
                        )

//                        InsertNewCashFlowContent(
//                            newCashflowViewModel
//                        )


//                        FixedEntryScreen(
//                            onBack = { finish() }
//                        )

//                val navController = rememberNavController()
//                val goalViewModel: GoalViewModel = viewModel()
//
//                NavHost(
//                    navController = navController,
//                    startDestination = "home"
//                ) {
//
//                    composable("home") {
//                        HomeScreen(
//                            goals = goalViewModel.goals,
//                            onCreateGoalClick = {
//                                navController.navigate("createGoal")
//                            },
//                            onAddMoney = { id, amount ->
//                                goalViewModel.addMoney(id, amount)
//                            },
//                            onRemoveGoal = { id ->
//                                goalViewModel.removeGoal(id)
//                            }
//                        )
//                    }
//                    composable("createGoal") {
//                        CreateGoalScreen(
//                            onSaveGoal = { name, amount ->
//                                goalViewModel.addGoal(name, amount)
//                                navController.popBackStack()
//                            },
//                            onBackClick = {
//                                navController.popBackStack()
//                            }
//                        )
//                    }
//                }

                    }
                }
            }
        }
    }
}