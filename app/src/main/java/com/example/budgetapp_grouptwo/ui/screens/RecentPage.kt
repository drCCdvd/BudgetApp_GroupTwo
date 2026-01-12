package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RecentPage(
    navController: NavController,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        NavigationMenu(navController)
        Text ("Recent Page")
        Test()
    }
}


@Composable
fun Test(
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Red)
    ) {
        Column(
            modifier = Modifier.border(2.dp, Color.Blue)
        ) {
            Text("23. Okt")
            Text("Mobilepay (Far)")
        }
        Text("45kr.")
        Text("Fjern")
    }

}



