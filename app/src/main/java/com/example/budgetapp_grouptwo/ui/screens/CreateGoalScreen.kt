package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.Header
import com.example.budgetapp_grouptwo.ui.utils.CurrencyVisualTransformation
import com.example.budgetapp_grouptwo.ui.utils.DateMillisToLocaleDate
import org.intellij.lang.annotations.JdkConstants
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoalScreen(
    navController: NavController,
    onSaveGoal: (String, Double, LocalDate) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var expandedDatePicker by remember{mutableStateOf(false)}
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var datePickerState = rememberDatePickerState();
    var dateText by remember { mutableStateOf("") };

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
            modifier = Modifier.fillMaxWidth()
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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box{
            if(selectedDate==null){
                dateText = "Vælg dato"
            }else{
                dateText = selectedDate.toString();
            }

            Button(onClick = {
                expandedDatePicker=true;
            }) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    Text(text = dateText)
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Date picker icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black,
                        )
                }
            }


            if(expandedDatePicker){
                Popup (
                    onDismissRequest = {
                        expandedDatePicker=false
                        selectedDate = DateMillisToLocaleDate(datePickerState.selectedDateMillis).toLocaleDate();
                    }

                ){
                    Box(){
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false,
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                if (name.isNotBlank() && parsedAmount != null) {
                    onSaveGoal(name, parsedAmount, selectedDate?: LocalDate.now());
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tilføj")
        }
    }
}