package com.example.budgetapp_grouptwo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetapp_grouptwo.CashFlowStorage
import com.example.budgetapp_grouptwo.ui.Header
import com.example.budgetapp_grouptwo.RegularCashFlow


@Composable
fun FixedEntryScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    // ---- State ----
    var incomeText by remember { mutableStateOf("") }
    var expenseText by remember { mutableStateOf("") }

    // ---- Load saved values once ----
    LaunchedEffect(Unit) {
        val earnings = CashFlowStorage.loadRegularEarnings(context)
        val expenses = CashFlowStorage.loadRegularExpenses(context)

        RegularCashFlow.setRegularEarnings(earnings)
        RegularCashFlow.setRegularExpense(expenses)

        incomeText = if (earnings > 0.0) earnings.toString() else ""
        expenseText = if (expenses > 0.0) expenses.toString() else ""
    }

    // ---- Colors
    val bg = Color(0xFFF7F7FB)
    val fieldBg = Color(0xFFEFEFF3)
    val primary = Color(0xFFBFD3FF)
    val textGrey = Color(0xFF777777)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Header("Rediger faste", onBack=onBack);

        // ---- Line 2: Indtægt ----
        Text("Indtægt", color = textGrey, modifier = Modifier.width(260.dp))
        Spacer(Modifier.height(8.dp))

        // ---- Line 3: field Indtægt ----
        OutlinedTextField(
            value = incomeText,
            onValueChange = { incomeText = it },
            placeholder = { Text("0 kr.") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(18.dp))

        // ---- Line 4: Udgift ----
        Text("Udgift", color = textGrey, modifier = Modifier.width(260.dp))
        Spacer(Modifier.height(8.dp))

        // ---- Line 5: field Udgift ----
        OutlinedTextField(
            value = expenseText,
            onValueChange = { expenseText = it },
            placeholder = { Text("0 kr.") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(26.dp))

        // ---- Line 6: Gem ----

        Button(
            onClick = {
                val income = incomeText.replace(",", ".").trim().toDoubleOrNull()
                val expense = expenseText.replace(",", ".").trim().toDoubleOrNull()

                // cho phép để trống (coi như 0)
                val incomeFinal = income ?: if (incomeText.trim().isEmpty()) 0.0 else return@Button Toast
                    .makeText(context, "Indtægt không hợp lệ", Toast.LENGTH_SHORT).show()

                val expenseFinal = expense ?: if (expenseText.trim().isEmpty()) 0.0 else return@Button Toast
                    .makeText(context, "Udgift không hợp lệ", Toast.LENGTH_SHORT).show()

                // Save to in-memory + SharedPreferences
                RegularCashFlow.setRegularEarnings(incomeFinal)
                CashFlowStorage.saveRegularEarnings(context, incomeFinal)

                RegularCashFlow.setRegularExpense(expenseFinal)
                CashFlowStorage.saveRegularExpenses(context, expenseFinal)

                Toast.makeText(context, "Gemt!", Toast.LENGTH_SHORT).show()

                // sau khi hien chu Gemt! nen cho quay lai trang truoc
                //onBack()
            },
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary)
        ) {
            Text("Gem", color = Color(0xFF4B4B4B))
        }
    }
}

