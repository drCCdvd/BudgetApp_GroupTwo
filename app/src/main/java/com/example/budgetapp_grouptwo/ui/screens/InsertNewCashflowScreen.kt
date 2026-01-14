package com.example.budgetapp_grouptwo.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetapp_grouptwo.R
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.Header
import kotlinx.serialization.internal.throwMissingFieldException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.math.ln


//Todo, fjern Class component activity
class InsertNewCashflowScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cashFlow = CashFlow();
        val newCashflowViewModel = NewCashflowViewModel(cashFlow);

        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_new_cashflow_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContent {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun contentPreview(){

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewCashFlowContent(
    onBack: () -> Unit,
    cashFlowViewModel: CashFlowViewModel,
    onSubmit: () -> Unit,
){
    //var newCashflowViewModel = newCashFlowViewModel

    //if(newCashFlowViewModel==null){
    //    throw error("No viewmodel")
    //}

    //var _cashFlow = newCashflowViewModel.cashFlow;
    var expandedCategoryMenu by remember { mutableStateOf(false) }

    var expandedDatePicker by remember{mutableStateOf(false)}
    var selectedDate by remember { mutableStateOf("") }
    var datePickerState = rememberDatePickerState();
    var dateText = "";

    //States for new cashflow insert
    var newName by remember { mutableStateOf("") };
    var newAmount by remember { mutableStateOf("") };
    var newType by remember { mutableStateOf("expense") }


    //Content
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        Header("Indsæt ny udgift/indtægt", onBack = onBack);

        //Name field
        TextField(
            label = {Text(text = "Navn")},
            value = newName,
            onValueChange = {newName = it}
        )
        
        //Amount field
        TextField(
            label = {Text(text = "Beløb")},
            value = newAmount,
            onValueChange = {newAmount = it}
        )

        Box(){
            Row {
                Button (onClick = {expandedCategoryMenu=true}) {
                    Text(
                        text = "Vælg udgift",
                    )
                }
                Text(
                    text = newType,
                )
            }
            //Dropdown category
            DropdownMenu (
                expanded = expandedCategoryMenu,
                onDismissRequest = {expandedCategoryMenu=false}
            ){
                DropdownMenuItem(
                    text = {Text("Udgift")},
                    onClick = {
                        newType = "expense"
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text("Indtjening")
                    },
                    onClick = {
                        newType = "income"
                    }
                )
            }
        }

        Box{
            if(selectedDate!=""){
                dateText=selectedDate.toString();
            }else{
                dateText="Vælg dato"
            }

            Button(onClick = {expandedDatePicker=true}) {
                Text(text = dateText)
            }

            if(expandedDatePicker){
                Popup (
                    onDismissRequest = {expandedDatePicker=false}
                ){
                    Box(){
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false

                        )
                    }
                }
            }
        }
        //Save button
        Button(onClick = {
            val newAmountInDouble: Double = newAmount.toDoubleOrNull()?:0.0;
            val newDateInMillis = datePickerState.selectedDateMillis
            val newType = newType
            var dateFormatted = LocalDate.now();
            if(newDateInMillis!=null){
                dateFormatted = Instant.ofEpochMilli(newDateInMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            }

            //Add cashflow to Cashflow instance based on type of cashflow
            if(newType=="expense"){
                cashFlowViewModel.addExpense(Expense(name=newName, amount = newAmountInDouble, date = dateFormatted, type = ExpenseType.RegularExpense))
            }else {
                cashFlowViewModel.addIncome(Income(name=newName, amount = newAmountInDouble, date = dateFormatted))
            }
            onSubmit();

        }) {
            Text(text = "Gem")
        }
    }
}