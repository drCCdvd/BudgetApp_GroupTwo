package com.example.budgetapp_grouptwo.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.Header
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

private val LightBlue = Color(0xFFBFD6FF)

class InsertNewCashflowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

        }
    }
}
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

    val dateLabel = datePickerState.selectedDateMillis?.let { millis ->
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().toString()
    } ?: "I dag"

    fun sanitizeAmount(input: String): String =
        input.filter { it.isDigit() || it == '.' || it == ',' }

    val canSave = newAmount.trim().isNotBlank() && newName.trim().isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))

        Header("Ny post", onBack = onBack)

        Spacer(Modifier.height(36.dp))

        FieldLabel("Beløb")
        PillTextField(
            value = newAmount,
            placeholder = "0 kr.",
            onValueChange = { newAmount = sanitizeAmount(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textAlignCenter = true
        )
        Spacer(Modifier.height(18.dp))

        FieldLabel("Post")
        PillTextField(
            value = newName,
            placeholder = "Beskrivelse",
            onValueChange = { newName = it },
            keyboardOptions = KeyboardOptions.Default,
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
        PillPicker(
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
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant

            )
        ) {
            Text(
                text = if (newType == "expense") "Gem udgift" else "Gem indtægt",
                color = if (canSave) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}
@Composable
private fun PillTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    textAlignCenter: Boolean
) {
    Surface(
        modifier = Modifier.width(220.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(
                fontSize = 14.sp,
                textAlign = if (textAlignCenter) TextAlign.Center else TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        textAlign = if (textAlignCenter) TextAlign.Center else TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                inner()
            }
        )
    }
}
@Composable
private fun PillPicker(
    text: String,
    onClick : () -> Unit,
) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .height(46.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
@Composable
private fun TypePill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background =
        if (selected) LightBlue
        else MaterialTheme.colorScheme.surfaceVariant

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