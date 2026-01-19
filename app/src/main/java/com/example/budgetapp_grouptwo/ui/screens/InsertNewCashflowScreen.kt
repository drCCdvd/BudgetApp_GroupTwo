package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.budgetapp_grouptwo.ViewModel.NewCashflowViewModel
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.Header
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Preview(showSystemUi = true)
@Composable
fun ContentPreview() {
    val cashFlow = CashFlow()
    val vm = remember { NewCashflowViewModel(cashFlow) }
    InsertNewCashFlowContent(
        onBack = {},
        newCashFlowViewModel = vm
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewCashFlowContent(
    onBack: () -> Unit,
    newCashFlowViewModel: NewCashflowViewModel
) {
    val vm = newCashFlowViewModel
    val datePickerState = rememberDatePickerState()

    var expandedCategoryMenu by remember { mutableStateOf(false) }
    var expandedDatePicker by remember { mutableStateOf(false) }

    // Chọn loại form đang nhập: expense / income
    var newType by remember { mutableStateOf("expense") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header("Indsæt ny udgift/indtægt", onBack = onBack)

        // Name field (bind vào ViewModel)
        TextField(
            label = { Text("Navn") },
            value = if (newType == "expense") vm.expenseName else vm.incomeName,
            onValueChange = { v ->
                if (newType == "expense") vm.onExpenseNameChange(v)
                else vm.onIncomeNameChange(v)
            }
        )

        // Amount field (bind vào ViewModel)
        TextField(
            label = { Text("Beløb") },
            value = if (newType == "expense") vm.expenseAmountText else vm.incomeAmountText,
            onValueChange = { v ->
                if (newType == "expense") vm.onExpenseAmountChange(v)
                else vm.onIncomeAmountChange(v)
            }
        )

        // Dropdown chọn Income/Expense
        Box {
            Row {
                Button(onClick = { expandedCategoryMenu = true }) {
                    Text(text = "Vælg type")
                }
                Text(text = "  $newType")
            }

            DropdownMenu(
                expanded = expandedCategoryMenu,
                onDismissRequest = { expandedCategoryMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Udgift") },
                    onClick = {
                        newType = "expense"
                        expandedCategoryMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Indtjening") },
                    onClick = {
                        newType = "income"
                        expandedCategoryMenu = false
                    }
                )
            }
        }

        // Date picker // gan tam nut gia
        Box {
            val dateText =
                if (datePickerState.selectedDateMillis != null) "Dato valgt"
                else "Vælg dato"

            Button(onClick = { expandedDatePicker = true }) {
                Text(text = dateText)
            }

            if (expandedDatePicker) {
                Popup(onDismissRequest = { expandedDatePicker = false }) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
//        // gan tam nut nay
//        Button(onClick = { /* tạm bỏ chọn ngày */ }) {
//            Text("Vælg dato (tạm tắt)")
//        }

        // Save button
        Button(onClick = {
            val millis = datePickerState.selectedDateMillis
            val dateFormatted =
                if (millis != null) {
                    Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                } else LocalDate.now()

            if (newType == "expense") {
                vm.onExpenseDateChange(dateFormatted)
                vm.onExpenseTypeChange(ExpenseType.RegularExpense) // sau này bạn có thể làm dropdown chọn FIXED/DepositToGoal
                vm.submitExpense()
            } else {
                vm.onIncomeDateChange(dateFormatted)
                vm.submitIncome()
            }
        }) {
            Text(text = "Gem")
        }

        // List hiển thị cashflows
        val list = vm.cashFlow.cashFlows
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(list.size) { i ->
                val item = list[i]
                val prefix = if (item is Income) "+" else "-"
                Text(text = "${item.name}: $prefix${item.amount}, ${item.dateAdded}")
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InsertNewCashFlowContent(onBack: () -> Unit, newCashFlowViewModel: NewCashflowViewModel){
//    var newCashflowViewModel = newCashFlowViewModel
//
//    if(newCashFlowViewModel==null){
//        throw error("No viewmodel")
//    }
//
//    var _cashFlow = newCashflowViewModel.cashFlow;
//    var expandedCategoryMenu by remember { mutableStateOf(false) }
//
//    var expandedDatePicker by remember{mutableStateOf(false)}
//    var selectedDate by remember { mutableStateOf("") }
//    var datePickerState = rememberDatePickerState();
//    var dateText = "";
//
//    //States for new cashflow insert
//    var newName by remember { mutableStateOf("") };
//    var newAmount by remember { mutableStateOf("") };
//    var newType by remember { mutableStateOf("expense") }
//
//
//    //Content
//    Column (
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//
//
//        Header("Indsæt ny udgift/indtægt", onBack = onBack);
//
//        //Name field
//        TextField(
//            label = {Text(text = "Navn")},
//            value = newName,
//            onValueChange = {newName = it}
//        )
//
//        //Amount field
//        TextField(
//            label = {Text(text = "Beløb")},
//            value = newAmount,
//            onValueChange = {newAmount = it}
//        )
//
//        Box(){
//            Row {
//                Button (onClick = {expandedCategoryMenu=true}) {
//                    Text(
//                        text = "Vælg udgift",
//                    )
//                }
//                Text(
//                    text = newType,
//                )
//            }
//            //Dropdown category
//            DropdownMenu (
//                expanded = expandedCategoryMenu,
//                onDismissRequest = {expandedCategoryMenu=false}
//            ){
//                DropdownMenuItem(
//                    text = {Text("Udgift")},
//                    onClick = {
//                        newType = "expense"
//                    }
//                )
//                DropdownMenuItem(
//                    text = {
//                        Text("Indtjening")
//                    },
//                    onClick = {
//                        newType = "income"
//                    }
//                )
//            }
//        }
//
//        Box{
//            if(selectedDate!=""){
//                dateText=selectedDate.toString();
//            }else{
//                dateText="Vælg dato"
//            }
//
//            Button(onClick = {expandedDatePicker=true}) {
//                Text(text = dateText)
//            }
//
//            if(expandedDatePicker){
//                Popup (
//                    onDismissRequest = {expandedDatePicker=false}
//                ){
//                    Box(){
//                        DatePicker(
//                            state = datePickerState,
//                            showModeToggle = false
//
//                        )
//                    }
//                }
//            }
//        }
//        //Save button
//        Button(onClick = {
//            val newAmountInDouble: Double = newAmount.toDoubleOrNull()?:0.0;
//            val newDateInMillis = datePickerState.selectedDateMillis
//            val newType = newType
//            var dateFormatted = LocalDate.now();
//            if(newDateInMillis!=null){
//                dateFormatted = Instant.ofEpochMilli(newDateInMillis)
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//            }
//
//            //Add cashflow to Cashflow instance based on type of cashflow
//            if(newType=="expense"){
//                newCashFlowViewModel.addExpense(newName, newAmountInDouble, dateFormatted, ExpenseType.RegularExpense);
//            }else {
//                newCashFlowViewModel.addIncome(newName, newAmountInDouble, dateFormatted)
//            }
//
//        }) {
//            Text(text = "Gem")
//        }
//
//
//        var listLength = _cashFlow.cashFlows.size;
//
//        LazyColumn(modifier= Modifier.padding(10.dp)) {
//            items(listLength){i ->
//
//                var list = newCashFlowViewModel.cashFlow.cashFlows
//
//                var prefix = "";
//                if(newCashFlowViewModel.cashFlow.cashFlows.get(i) is Income){
//                    prefix = "+";
//                }else{
//                    prefix = "-";
//                }
//                Text(text = "${list.get(i).name}: ${prefix}${list.get(i).amount}, ${list.get(i).dateAdded}");
//            }
//        }
//
//    }
//}
//
//@Composable
//fun ListOfDetails(cashFlowList: List<Cash>, modifier: Modifier){
//
//    var listLength = cashFlowList.size;
//
//    LazyColumn(modifier=modifier) {
//        items(listLength){i ->
//            var prefix = "";
//            if(cashFlowList.get(i) is Income){
//                prefix = "+";
//            }else{
//                prefix = "-";
//            }
//            Text(text = "${cashFlowList.get(i).name}: ${prefix}${cashFlowList.get(i).amount}, ");
//        }
//    }
//}