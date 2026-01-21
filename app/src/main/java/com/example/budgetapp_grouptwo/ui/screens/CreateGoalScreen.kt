package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.Header
import com.example.budgetapp_grouptwo.ui.components.FieldLabel
import com.example.budgetapp_grouptwo.ui.components.LabelTitle
import com.example.budgetapp_grouptwo.ui.components.PillPicker
import com.example.budgetapp_grouptwo.ui.components.PillTextField
import com.example.budgetapp_grouptwo.ui.utils.CurrencyVisualTransformation
import com.example.budgetapp_grouptwo.ui.utils.DateMillisToLocaleDate
import org.intellij.lang.annotations.JdkConstants
import java.time.LocalDate

private val LightBlue = Color(0xFFBFD6FF)


@Preview
@Composable
fun createGoalScreenPreview(){
    var value = "";
    PillTextField(value = "Hello", placeholder = "Beløb", onValueChange = {value = ""}, keyboardOptions = KeyboardOptions.Default, textAlignCenter = false)
}

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

    val canSave = amount.trim().isNotBlank() && name.trim().isNotBlank() && selectedDate!=null


    Header("Opret nyt mål", onBack=onBack)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            FieldLabel("Nyt mål")
            PillTextField(
                value = name,
                onValueChange = {name = it},
                placeholder = "Hvad sparer du op til?",
                keyboardOptions = KeyboardOptions.Default,
                textAlignCenter = true,
            )

            /*OutlinedTextField(
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
            )*/


            Spacer(modifier = Modifier.height(16.dp))

            FieldLabel("Beløb for mål")

            PillTextField(
                value = amount,
                placeholder = "0 kr.",
                onValueChange = {newValue ->
                    amount = newValue.filter { it.isDigit() }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textAlignCenter = true,
                visualTransformation = CurrencyVisualTransformation(),
            )

            /*OutlinedTextField(
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
            )*/

            Spacer(modifier = Modifier.height(16.dp))


            FieldLabel("Slut dato")
            Box{

                if(selectedDate==null){
                    dateText = "Vælg dato"
                }else{
                    dateText = selectedDate.toString();
                }

                PillPicker(
                    text = dateText,
                    onClick = {expandedDatePicker=true}
                )

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
                enabled = canSave,
                modifier = Modifier
                    .width(280.dp)
                    .height(54.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC7DAFF),
                        contentColor = Color.Black,
                        disabledContainerColor = Color.hsv(0f,0f,.85f)
                    )

            ) {
                Text(text = "Opret mål",
                    color = if (canSave) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
    }
}