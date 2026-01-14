package com.example.budgetapp_grouptwo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import androidx.glance.appwidget.compose
import androidx.navigation.NavController


@Composable
fun HomePage(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
    NavigationMenu(navController) //
    Text("homPage")
        //add more a buttom for my fage
        androidx.compose.material3.Button(
            onClick = { navController.navigate("editRegularCashflow") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Gå til Fast (Fixed Expenses)")
        }
}
}
// tao nut ket noi cac trang

