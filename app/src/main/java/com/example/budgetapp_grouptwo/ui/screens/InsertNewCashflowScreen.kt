package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.components.Header
import com.example.budgetapp_grouptwo.ui.components.FieldLabel
import com.example.budgetapp_grouptwo.ui.components.PillPicker
import com.example.budgetapp_grouptwo.ui.components.PillTextField
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

private val LightBlue = Color(0xFFBFD6FF)

/**The view of creating new CashFlow (either income or expense)
 * @onBack is the function navigating back
 * @OnSubmit is the function for submitting the new Instance (either Income or Expense)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNewCashflowScreen(
    onBack: () -> Unit,
    cashFlowViewModel: CashFlowViewModel,
    onSubmit: () -> Unit,
) {
    var expandedDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var newName by rememberSaveable { mutableStateOf("") }
    var newAmount by rememberSaveable { mutableStateOf("") }
    var newType by rememberSaveable { mutableStateOf("expense") }

    //Convert the given date in milliseconds to localeDate
    val dateLabel = datePickerState.selectedDateMillis?.let { millis ->
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().toString()
    } ?: "I dag"

    fun sanitizeAmount(input: String): String =
        input.filter { it.isDigit() || it == '.' || it == ',' }

    val canSave = newAmount.trim().isNotBlank() && newName.trim().isNotBlank()

    Header("Ny post", onBack = onBack)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.height(12.dp))


        Spacer(Modifier.height(36.dp))

        FieldLabel("Post")
        PillTextField(
            value = newName,
            placeholder = "Beskrivelse",
            onValueChange = { newName = it },
            keyboardOptions = KeyboardOptions.Default,
            textAlignCenter = true
        )
        Spacer(Modifier.height(18.dp))
        FieldLabel("Beløb")
        PillTextField(
            value = newAmount,
            placeholder = "0 kr.",
            onValueChange = { newAmount = sanitizeAmount(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textAlignCenter = true
        )
        Spacer(Modifier.height(18.dp))


        FieldLabel("Type")
        Row(
            modifier = Modifier.width(220.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TypePill(
                text = "Udgift",
                selected = newType == "expense",
                onClick = { newType = "expense" },
                modifier = Modifier.weight(1f)
            )
            TypePill(
                text = "Indtægt",
                selected = newType == "income",
                onClick = { newType = "income" },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(18.dp))

        FieldLabel("Dato")
        PillPicker (
            text = dateLabel,
            onClick = { expandedDatePicker = true }
        )
        if (expandedDatePicker) {
            Popup(onDismissRequest = { expandedDatePicker = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 6.dp
                ) {
                    Column(Modifier.padding(12.dp)) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )
                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { expandedDatePicker = false }) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(36.dp))

        Button(
            onClick = {
                val amountDouble = newAmount.replace(',','.').toDoubleOrNull() ?: 0.0

                val dateFormatted = datePickerState.selectedDateMillis?.let {millis ->
                    Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                } ?: LocalDate.now()

                if (newType == "expense") {
                    cashFlowViewModel.addExpense(
                        Expense(
                            name = newName,
                            amount = amountDouble,
                            date = dateFormatted,
                            type = ExpenseType.RegularExpense
                        )
                    )
                } else {
                    cashFlowViewModel.addIncome(
                        Income(
                            name = newName,
                            amount = amountDouble,
                            date = dateFormatted
                        )
                    )
                }
                onSubmit()
            },
            enabled = canSave,
            modifier = Modifier
                .width(280.dp)
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue,
                disabledContainerColor = Color.hsv(0f,0f,.85f)

            )
        ) {
            Text(
                text = if (newType == "expense") "Gem udgift" else "Gem indtægt",
                color = if (canSave) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** UI detail of choosing either expense or income
 * (with rounded corners)
 */
@Composable
private fun TypePill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background =
        if (selected) LightBlue
        else Color.hsv(0f,0f,.95f)

    val contentColor =
        if (selected) Color.White
        else MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = modifier
            .height(44.dp),
        shape = RoundedCornerShape(12.dp),
        color = background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                color = contentColor
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PillsPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FieldLabel("Beløb")
        PillTextField(
            value = "",
            placeholder = "0 kr.",
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textAlignCenter = true
        )
        Spacer(Modifier.height(18.dp))
        FieldLabel("Type")
        Row(
            modifier = Modifier.width(220.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TypePill(
                text = "Udgift",
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            TypePill(
                text = "Indtægt",
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}