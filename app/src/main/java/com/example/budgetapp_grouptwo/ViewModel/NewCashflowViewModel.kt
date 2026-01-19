package com.example.budgetapp_grouptwo.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.ExpenseType
import java.time.LocalDate


class NewCashflowViewModel(
    val cashFlow: CashFlow
) : ViewModel() {

    // ---------- UI state: INDKOMST (Income) ----------
    var incomeName by mutableStateOf("")
        private set

    var incomeAmountText by mutableStateOf("")
        private set

    var incomeDate by mutableStateOf(LocalDate.now())
        private set

    // ---------- UI state: UDGIFT (Expense) ----------
    var expenseName by mutableStateOf("")
        private set

    var expenseAmountText by mutableStateOf("")
        private set

    var expenseDate by mutableStateOf(LocalDate.now())
        private set

    var expenseType by mutableStateOf(ExpenseType.RegularExpense)
        private set

    // ---------- UI events ----------
    fun onIncomeNameChange(v: String) { incomeName = v }
    fun onIncomeAmountChange(v: String) { incomeAmountText = v }
    fun onIncomeDateChange(v: LocalDate) { incomeDate = v }

    fun onExpenseNameChange(v: String) { expenseName = v }
    fun onExpenseAmountChange(v: String) { expenseAmountText = v }
    fun onExpenseDateChange(v: LocalDate) { expenseDate = v }
    fun onExpenseTypeChange(v: ExpenseType) { expenseType = v }

    // ---------- Actions ----------
    fun submitIncome(): Boolean {
        val name = incomeName.trim()
        val amount = incomeAmountText.toDoubleOrNull() ?: return false
        if (name.isBlank()) return false

        cashFlow.addNewIncome(name, amount, incomeDate)

        // reset (tuỳ bạn)
        incomeName = ""
        incomeAmountText = ""
        incomeDate = LocalDate.now()
        return true
    }

    fun submitExpense(): Boolean {
        val name = expenseName.trim()
        val amount = expenseAmountText.toDoubleOrNull() ?: return false
        if (name.isBlank()) return false

        cashFlow.addNewExpense(name, amount, expenseType, expenseDate)

        // reset (tuỳ bạn)
        expenseName = ""
        expenseAmountText = ""
        expenseDate = LocalDate.now()
        expenseType = ExpenseType.RegularExpense
        return true
    }

    // Nếu bạn vẫn muốn giữ 2 hàm cũ:
    fun addExpense(name: String, amount: Double, date: LocalDate, type: ExpenseType) {
        cashFlow.addNewExpense(name, amount, type, date)
    }

    fun addIncome(name: String, amount: Double, date: LocalDate) {
        cashFlow.addNewIncome(name, amount, date)
    }
}

//package com.example.budgetapp_grouptwo.ViewModel
//
//import androidx.lifecycle.ViewModel
//import com.example.budgetapp_grouptwo.model.CashFlow
//import com.example.budgetapp_grouptwo.model.ExpenseType
//import java.time.LocalDate
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//
//class NewCashflowViewModel(cashFlow: CashFlow): ViewModel() {
//    //Initialize cash flow object
//    var cashFlow = cashFlow;
//
//    fun addExpense(name: String, amount: Double, date: LocalDate, type: ExpenseType){
//        cashFlow.addNewExpense(name, amount, type, date);
//    }
//
//    fun addIncome(name: String, amount: Double, date: LocalDate){
//        cashFlow.addNewIncome(name, amount, date);
//    }
//
//    //Returns cashflow data
//    fun getCashflowData(){
//        //todo implement
//    }
//}