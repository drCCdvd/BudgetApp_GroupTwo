package com.example.budgetapp_grouptwo.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.budgetapp_grouptwo.viewmodel.RecentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun RecentPage(
    navController: NavController,
    viewModel: RecentViewModel = viewModel()
) {
    val items = viewModel.recentItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        NavigationMenu(navController)

        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(items) { item ->
                RecentItemCard(
                    item = item,
                    onRemoveClick = { viewModel.removeItem(item.id) }

                )
            }
        }
    }

}




