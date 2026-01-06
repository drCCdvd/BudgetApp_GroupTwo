package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.budgetapp_grouptwo.ui.home.HomeScreen
import com.example.budgetapp_grouptwo.ui.goal.CreateGoalScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel = GoalViewModel()

            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        onCreateGoalClick = {
                            navController.navigate("create")
                        }
                    )
                }
                composable("create") {
                    CreateGoalScreen { name, amount ->
                        viewModel.addGoal(
                            Goal(name, amount, 0, 0, 0)
                        )
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudgetApp_GroupTwoTheme {
        Greeting("Android")
    }
}