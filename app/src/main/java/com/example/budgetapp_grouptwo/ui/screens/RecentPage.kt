package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.budgetapp_grouptwo.ui.RecentTransaktions.RecentItemCard
import com.example.budgetapp_grouptwo.utils.FilterType
import com.example.budgetapp_grouptwo.ViewModel.RecentViewModel

@Composable
fun SegmentButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.surface

    val textColor = if (selected)
        MaterialTheme.colorScheme.onPrimary
    else
        MaterialTheme.colorScheme.onSurface

    androidx.compose.material3.Button(
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = background
        )
    ) {
        Text(text, color = textColor)
    }
}

@Composable
fun RecentPage(
    navController: NavController,
    viewModel: RecentViewModel = viewModel()

) {
    val items = viewModel.filteredItems


    val filter = viewModel.filter.value




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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            SegmentButton(
                text = "Faste",
                selected = filter == FilterType.FIXED,
                onClick = { viewModel.setFilter(FilterType.FIXED) }
            )
            SegmentButton(
                text = "Alle",
                selected = filter == FilterType.ALL,
                onClick = { viewModel.setFilter(FilterType.ALL) }
            )
            SegmentButton(
                text = "Variable",
                selected = filter == FilterType.VARIABLE,
                onClick = { viewModel.setFilter(FilterType.VARIABLE) }
            )
        }
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




