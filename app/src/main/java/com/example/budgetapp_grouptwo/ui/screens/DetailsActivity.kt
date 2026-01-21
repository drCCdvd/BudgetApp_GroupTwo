package com.example.budgetapp_grouptwo.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.R
import com.example.budgetapp_grouptwo.ViewModel.CashFlowViewModel
import com.example.budgetapp_grouptwo.ViewModel.DetailsViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import com.example.budgetapp_grouptwo.ui.components.LabelTitle
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete



class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContent {

        }
    }
}

@Composable
fun DetailsContent(
    cashFlow: List<Cash>,
    navController: NavController,
    onRemoveIncome: (Int) -> Unit,
    onRemoveExpense: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp)
    ) {

        val grouped = cashFlow
            .sortedByDescending { it.dateAdded }
            .groupBy { cash ->
                cash.dateAdded.format(
                    DateTimeFormatter.ofPattern("MMMM yyyy", Locale("da"))
                )
            }

        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
            grouped.forEach { (month, items) ->

                // Sticky månedstitel
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = month.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.LightGray)
                        )
                    }
                }

                // Transaktioner med divider
                items(items) { cash ->
                    Column {
                        CashItem(
                            cash = cash,
                            onRemove = { id ->
                                if (cash is Expense) onRemoveExpense(id)
                                else onRemoveIncome(id)
                            }
                        )
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}


@Composable
fun CashItem(
    cash: Cash,
    onRemove: ((Int) -> Unit)? = null   // valgfri callback
) {
    var showConfirm by remember { mutableStateOf(false) }

    val isIncome = cash is Income
    val color = if (isIncome) Color(0xFF4CAF50) else Color(0xFFF44336)
    val prefix = if (isIncome) "+" else "-"
    val formattedDate = cash.dateAdded.format(
        DateTimeFormatter.ofPattern("d MMMM", Locale("da"))
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                //text = cash.dateAdded.toString(),
                text = formattedDate,
                color = Color(0xFF808080),
                fontSize = 10.sp,
            )

            Text(
                text = cash.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal, fontSize = 18.sp,
                modifier = Modifier.width(180.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$prefix${cash.amount},-",
                color = color,
                style = MaterialTheme.typography.bodyLarge
            )

            // Kun vis knappen hvis onRemove != null
            if (onRemove != null) {
                Spacer(modifier = Modifier.width(16.dp))
// ...

                TextButton(onClick = { showConfirm = true }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Slet",
                        tint = Color.Gray
                    )
                }

                // Dialog kun hvis onRemove != null
                if (showConfirm && onRemove != null) {
                    ConfirmDeleteDialog(
                        itemName = cash.name,
                        onConfirm = {
                            showConfirm = false
                            onRemove(cash.id)
                        },
                        onDismiss = { showConfirm = false }
                    )
                }
            }
        }
    }
}

    @Composable
    fun ConfirmDeleteDialog(itemName: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
        val safeName = itemName.ifBlank { "denne post" }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Fjern post") },
            text = { Text("Vil du slette \"$safeName\"?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Fjern")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Annuller")
                }

            }
        )
    }
