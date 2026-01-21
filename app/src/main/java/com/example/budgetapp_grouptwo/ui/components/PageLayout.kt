    package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.ui.screens.NavigationMenu
import androidx.compose.ui.graphics.Color

@Composable
fun PageLayout(
    navController: NavController,
    title: String,
    subtitle: String? = null,
    showEditRecurring: Boolean = false,
    onEditRecurringClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // 🔹 HEADER
        MainHeader(
            title = title,
            subtitle = subtitle,
            showEditRecurring = showEditRecurring,
            onEditRecurringClick = onEditRecurringClick
        )

        // 🔹 NAVIGATION – altid samme sted
        NavigationMenu(navController)

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            color = Color.LightGray.copy(alpha = 0.4f),
            thickness = 1.dp
        )

        // 🔹 SIDEINDHOLD
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            content()
        }
    }
}