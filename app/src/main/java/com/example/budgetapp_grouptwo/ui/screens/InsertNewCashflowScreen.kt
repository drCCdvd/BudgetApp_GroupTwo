package com.example.budgetapp_grouptwo.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgetapp_grouptwo.R
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
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

        val newCashflowViewModel = NewCashflowViewModel();

        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_new_cashflow_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContent {
            InsertNewCashFlowContent(
                newCashFlowViewModel = newCashflowViewModel
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun contentPreview(){

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewCashFlowContent(newCashFlowViewModel: NewCashflowViewModel){
    var newCashflowViewModel = newCashFlowViewModel
    if(newCashFlowViewModel==null){
        throw error("No viewmodel")
    }

    var _cashFlow = newCashflowViewModel.cashFlow;
    var expandedCategoryMenu by remember { mutableStateOf(false) }

    var expandedDatePicker by remember{mutableStateOf(false)}
    var selectedDate by remember { mutableStateOf("") }
    var datePickerState = rememberDatePickerState();
    var dateText = "";

    //States for new cashflow insert
    var newName by remember { mutableStateOf("") };
    var newAmount by remember { mutableStateOf("") };
    var newType by remember { mutableStateOf("expense") }



    Column (
        modifier = Modifier.padding(10.dp)
    ){
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
                newCashFlowViewModel.addExpense(newName, newAmountInDouble, dateFormatted, ExpenseType.RegularExpense);
            }else {
                newCashFlowViewModel.addIncome(newName, newAmountInDouble, dateFormatted)
            }

        }) {
            Text(text = "Gem")
        }


        var listLength = _cashFlow.cashFlows.size;

        LazyColumn(modifier= Modifier.padding(10.dp)) {
            items(listLength){i ->

                var list = newCashFlowViewModel.cashFlow.cashFlows

                var prefix = "";
                if(newCashFlowViewModel.cashFlow.cashFlows.get(i) is Income){
                    prefix = "+";
                }else{
                    prefix = "-";
                }
                Text(text = "${list.get(i).name}: ${prefix}${list.get(i).amount}, ${list.get(i).dateAdded}");
            }
        }

    }
}

@Composable
fun ListOfDetails(cashFlowList: List<Cash>, modifier: Modifier){

    var listLength = cashFlowList.size;

    LazyColumn(modifier=modifier) {
        items(listLength){i ->
            var prefix = "";
            if(cashFlowList.get(i) is Income){
                prefix = "+";
            }else{
                prefix = "-";
            }
            Text(text = "${cashFlowList.get(i).name}: ${prefix}${cashFlowList.get(i).amount}, ");
        }
    }
}