package com.example.budgetapp_grouptwo.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.R
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.DetailsViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import java.time.LocalDate

class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContent {

        }
    }
}

@Composable
fun DetailsContent(
    cashFlow: List<Cash>,
    navController: NavController,
    onRemoveIncome: (Int) -> Unit,
    onRemoveExpense: (Int) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Navigation menu øverst
        NavigationMenu(navController)

        var listLength = cashFlow.size;
        println(cashFlow);


        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(
                items=cashFlow
            ) { cash ->
                CashItem(
                    cash = cash,
                    onRemove = { id ->
                        if(cash is Expense){
                            onRemoveExpense(id);
                        }else{
                            onRemoveIncome(id);
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CashItem(
    cash: Cash,
    onRemove: (Int) -> Unit,
){
    var prefix = "";
    if (cash is Income) {
        prefix = "+";
    } else {
        prefix = "-";
    }

    Text(text = "${cash.name}: ${prefix}${cash.amount}, ${cash.dateAdded}");
    Button(
        onClick = {
            onRemove(cash.id);
        }
    ) {
        Text(text = "Slet");
    }
}


