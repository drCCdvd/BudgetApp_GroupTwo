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
import com.example.budgetapp_grouptwo.ViewModel.GoalViewModelFactory
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.datastorage.CashflowDataController
import com.example.budgetapp_grouptwo.model.datastorage.CashflowDataSettings
import com.example.budgetapp_grouptwo.model.datastorage.CashflowDataSettingsSerializer
import com.example.budgetapp_grouptwo.repository.AppDatabase
import com.example.budgetapp_grouptwo.repository.DatabaseProvider
import com.example.budgetapp_grouptwo.repository.GoalRepository
import com.example.budgetapp_grouptwo.ui.screens.CreateGoalScreen
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen
import com.example.budgetapp_grouptwo.ui.screens.GoalsPage
import com.example.budgetapp_grouptwo.ui.screens.HomePage
import com.example.budgetapp_grouptwo.ui.screens.InsertNewCashFlowContent
import com.example.budgetapp_grouptwo.ui.screens.RecentPage
import java.time.LocalDate

class MainActivity : ComponentActivity() {


    /*private val db by lazy { DatabaseProvider.getDatabase(this) }
    val repository by lazy { GoalRepository(db.goalDao()) }

    private val goalViewModel: GoalViewModel by viewModels{
        GoalViewModelFactory(repository)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var cashFlow: CashFlow = CashFlow();
        var newCashflowViewModel = NewCashflowViewModel(cashFlow);

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
                        RecentPage(navController = navController)
                    }
                    // Goal page
                    /*composable("goals") {
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
                                goalViewModel.addGoal(name, amount, LocalDate.now())
                                navController.popBackStack()
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }*/

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