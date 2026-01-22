package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModel
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModelFactory
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModelFactory
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.repository.CashFlowRepository
import com.example.budgetapp_grouptwo.repository.DatabaseProvider
import com.example.budgetapp_grouptwo.repository.GoalRepository
import com.example.budgetapp_grouptwo.repository.GoalSavedRepository
import com.example.budgetapp_grouptwo.ui.components.PageLayout
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage

import com.example.budgetapp_grouptwo.ui.screens.DetailsContent
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashflowScreen
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    private val db by lazy { DatabaseProvider.getDatabase(this) }
    val goalRepository by lazy { GoalRepository(db.goalDao()) }
    val goalSavedRepository by lazy { GoalSavedRepository(db.goalSavedDao()) }
    val cashFlowRepository by lazy { CashFlowRepository(db.cashFlowDao(),db.goalSavedDao()) }

    private val goalViewModel: GoalViewModel by viewModels{
        GoalViewModelFactory(goalRepository)
    }

    private val cashFlowViewModel: CashFlowViewModel by viewModels {
        CashFlowViewModelFactory(cashFlowRepository)
    }

    var currentDate = LocalDate.now();

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
                                LocalDate.of(currentDate.year, currentDate.month, 1),
                                currentDate,
                                LocalContext.current
                            )
                        cashFlowViewModel.getDisposableToday(currentDate,LocalContext.current);


                        PageLayout(
                            navController = navController,
                            title = "For i dag: " + cashFlowViewModel.disposableToday.value.toInt().toString()+",-",
                            subtitle = "Disponible for ${currentDate.month.getDisplayName(
                                java.time.format.TextStyle.FULL,
                                java.util.Locale("da", "DK")
                            )}: " + cashFlowViewModel.monthlyDisposable.value.toInt().toString()+",-",
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
                                onAddMoney = { goal, amount ->
                                    cashFlowViewModel.insertCashFlowAndLinkToGoal(
                                        expense = Expense
                                            (name="Overført til: ${goal.name}", amount = amount, date = currentDate, type = ExpenseType.DepositToGoal),
                                        goalId = goal.id
                                    )
                                    goalViewModel.addMoney(goal, amount)
                                },
                                onRemoveGoal = { id ->
                                    cashFlowViewModel.removeAllSavedAmount(id);
                                    goalViewModel.removeGoal(id)
                                }
                            )
                        }
                    }



                    // CREATE GOAL Page
                    composable("createGoal") {
                        CreateGoalScreen(
                            navController = navController,
                            onSaveGoal = { name, amount, endDate ->
                                goalViewModel.addGoal(name, amount, endDate, currentDate)
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
                        FixedEntryScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

