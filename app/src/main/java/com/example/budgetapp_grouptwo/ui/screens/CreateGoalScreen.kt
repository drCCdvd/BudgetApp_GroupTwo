package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.Header
import com.example.budgetapp_grouptwo.ui.utils.CurrencyVisualTransformation

@Composable

fun CreateGoalScreen(
    navController: NavController,
    onSaveGoal: (String, Double) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Header("Opret nyt mål", onBack=onBack)


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Hvad sparer du op til?") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                // Kun tillad tal
                amount = newValue.filter { it.isDigit() }
            },
            label = { Text("Beløb") },
            visualTransformation = CurrencyVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number // Keyboard er numpad
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                if (name.isNotBlank() && parsedAmount != null) {
                    onSaveGoal(name, parsedAmount)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tilføj")
        }
    }
}