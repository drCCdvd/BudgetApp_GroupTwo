package com.example.budgetapp_grouptwo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import androidx.navigation.compose.composable
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModelFactory
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModelFactory
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.repository.AppDatabase
import com.example.budgetapp_grouptwo.repository.CashFlowRepository
import com.example.budgetapp_grouptwo.repository.DatabaseProvider
import com.example.budgetapp_grouptwo.repository.GoalRepository
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.screens.DetailsContent
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashFlowContent
import com.example.budgetapp_grouptwo.ui.screens.RecentPage
import java.time.LocalDate

class MainActivity : ComponentActivity() {


    private val db by lazy { DatabaseProvider.getDatabase(this) }
    val goalRepository by lazy { GoalRepository(db.goalDao()) }
    val cashFlowRepository by lazy { CashFlowRepository(db.cashFlowDao()) }

    private val goalViewModel: GoalViewModel by viewModels{
        GoalViewModelFactory(goalRepository)
    }

    private val cashFlowViewModel: CashFlowViewModel by viewModels {
        CashFlowViewModelFactory(cashFlowRepository)
    }

    var cashFlow: CashFlow = CashFlow();
    var newCashflowViewModel = NewCashflowViewModel(cashFlow);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()

        setContent {
            BudgetApp_GroupTwoTheme {
                val navController = rememberNavController()

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
                        DetailsContent(
                            cashFlow = cashFlowViewModel.cashFlows,
                            navController=navController,
                            onRemoveIncome = { id ->
                                cashFlowViewModel.removeIncome(id)
                            },
                            onRemoveExpense = { id ->
                                cashFlowViewModel.removeExpense(id)
                            }
                        )
                    }
                    // Goal page
                    composable("goals") {
                        GoalsPage(
                            goals = goalViewModel.goals,
                            navController = navController,
                            onCreateGoalClick = {
                                navController.navigate("createGoal")
                            },
                            onAddMoney = { goal, amount ->
                                goalViewModel.addMoney(goal, amount)
                                cashFlowViewModel.addExpense(Expense(name = "Overført til mål: " + goal.name, amount = amount, date = LocalDate.now(), type = ExpenseType.DepositToGoal))
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
                                goalViewModel.addGoal(name, amount, LocalDate.now())
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
                            cashFlowViewModel = cashFlowViewModel,
                            onSubmit = {navController.navigate("home")}
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