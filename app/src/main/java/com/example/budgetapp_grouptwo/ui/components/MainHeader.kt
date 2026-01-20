package com.example.budgetapp_grouptwo.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.screens.NavigationMenu

@Composable
fun MainHeader(
    title: String,
    subtitle: String? = null,
    showEditRecurring: Boolean = false,
    onEditRecurringClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp)) // lidt luft mellem title og sub
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Light
                    )
                }
            }

            if (showEditRecurring && onEditRecurringClick != null) {
                TextButton(
                    onClick = onEditRecurringClick
                ) {
                    Text(
                        text = "Rediger faste",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}