package com.example.budgetapp_grouptwo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
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
import com.example.budgetapp_grouptwo.ui.screens.OverviewScreenWithQMenu
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
import com.example.budgetapp_grouptwo.ui.components.PageLayout
import com.example.budgetapp_grouptwo.ui.components.QuickActionFabContainer
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage
import com.example.budgetapp_grouptwo.ui.screens.RecentPage

import com.example.budgetapp_grouptwo.ui.screens.DetailsContent
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashflowScreen
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
                        //preprocess disposable in cashflowviewmodel
                        //to get disposable for title and subtitle
                        cashFlowViewModel
                            .getDisposable(
                                LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1),
                                LocalDate.now(),
                                LocalContext.current
                            )
                        cashFlowViewModel.getDisposableToday(LocalContext.current);


                        PageLayout(
                            navController = navController,
                            title = "Tilbage i dag " + cashFlowViewModel.disposableToday.value.toInt().toString()+",-",
                            subtitle = "Disponible for ${LocalDate.now().month.name} " + cashFlowViewModel.monthlyDisposable.value.toInt().toString()+",-",
                            showEditRecurring = true,
                            onEditRecurringClick = {
                                navController.navigate("editRegularCashFlow")
                            }

                        ) {
                            HomePage(
                                navController = navController,
                                cashFlowViewModel=cashFlowViewModel,
                                goalViewModel=goalViewModel
                            )
                            QuickActionFabContainer(
                                navController = navController,
                            )
                        }
                    }
                    // RECENT PAGE
                    composable("recentDetails") {
                        PageLayout(
                            navController=navController,
                            title = "Seneste",
                            subtitle = "Overblik",
                        ) {
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
                            QuickActionFabContainer(
                                navController = navController,
                            )
                        }
                    }
                    // Goal page
                    composable("goals") {
                        PageLayout(
                            navController = navController,
                            title = "Mål",
                            subtitle = "Overblik"

                        ) {
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
                            QuickActionFabContainer(
                                navController = navController,
                            )
                        }
                    }



                    // CREATE GOAL Page
                    composable("createGoal") {
                        CreateGoalScreen(
                            navController = navController,
                            onSaveGoal = { name, amount, endDate ->
                                goalViewModel.addGoal(name, amount, endDate)
                                navController.popBackStack()
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // INSERT NEW CASHFLOW
                    composable("insertNewCashflow") {
                        InsertNewCashflowScreen(
                            onBack = { navController.popBackStack() },
                            cashFlowViewModel = cashFlowViewModel,
                            onSubmit = { navController.navigate("home") }
                        )
                    }

                    // EDIT REGULAR CASHFLOW
                    composable("editRegularCashflow") {
                        FixedEntryScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

