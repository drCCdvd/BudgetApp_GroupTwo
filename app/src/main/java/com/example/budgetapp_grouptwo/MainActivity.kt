package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    val expense1 = Expense(200, LocalDate.now(), ExpenseType.RegularExpense);
    val expense2 = Expense(200, LocalDate.now(), ExpenseType.RegularExpense),

    val listOfExpenses = listOf<Expense>(
        expense1,
        expense2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetApp_GroupTwoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListOfExpenses(listOfExpenses, modifier)
                }
            }
        }
    }
}

@Composable
fun ListOfExpenses(list: List<Expense>, modifier: Modifier){
    var listLength = list.size;

    for(i in 0..listLength-1){
        println(list.get(i).readOutLoud())
    }
}