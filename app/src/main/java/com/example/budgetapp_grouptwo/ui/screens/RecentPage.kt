package com.example.budgetapp_grouptwo.ui.screens

import android.text.Layout
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.util.unpackFloat1
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
            .border(1.dp, Color.Red)
            .padding(8.dp), // lidt mellemrum fra skærmkanten
            verticalAlignment = Alignment.CenterVertically, // centrer vertikalt
            horizontalArrangement = Arrangement.SpaceBetween // centrer horizontalt
    ) {
        // Left side:
        Column(
            modifier = Modifier
                .weight(5f) // filling to the left
                .border(1.dp, Color.Blue) // tykkelse af streg
        ) {
            Text("23. Okt")
            Text("Mobilepay (Far)", maxLines = 1, softWrap = false)
        }
        Spacer(modifier = Modifier.weight(1f)) // skubber indhold mod højre



        //Right side
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("45kr.", color = Color.Green)
            Spacer(modifier = Modifier.width(8.dp)) // mellem rum

            // Box med border
            Box(modifier = Modifier
                .border(2.dp, Color.Magenta)
                .padding(horizontal = 2.dp, vertical = 2.dp)
            ) {
                Text("Fjern")
            }
        }
    }
    // next line in "recents"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Red)
            .padding(8.dp), // lidt mellemrum fra skærmkanten
        verticalAlignment = Alignment.CenterVertically, // centrer vertikalt
        horizontalArrangement = Arrangement.SpaceBetween // centrer horizontalt
    ) {
        // Left side:
        Column(
            modifier = Modifier
                .weight(5f) // filling to the left
                .border(1.dp, Color.Blue) // tykkelse af streg
        ) {
            Text("23. Okt")
            Text("Elite kiosk", maxLines = 1, softWrap = false)
        }
        Spacer(modifier = Modifier.weight(1f)) // skubber indhold mod højre



        //Right side
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("-240kr.", color = Color.Red)
            Spacer(modifier = Modifier.width(8.dp)) // mellem rum

            // Box med border
            Box(modifier = Modifier
                .border(2.dp, Color.Magenta)
                .padding(horizontal = 2.dp, vertical = 2.dp)
            ) {
                Text("Fjern")
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Red)
            .padding(8.dp), // lidt mellemrum fra skærmkanten
        verticalAlignment = Alignment.CenterVertically, // centrer vertikalt
        horizontalArrangement = Arrangement.SpaceBetween // centrer horizontalt
    ) {
        // Left side:
        Column(
            modifier = Modifier
                .weight(5f) // filling to the left
                .border(1.dp, Color.Blue) // tykkelse af streg
        ) {
            Text("22. Okt")
            Text("Kælderbaren", maxLines = 1, softWrap = false)
        }
        Spacer(modifier = Modifier.weight(1f)) // skubber indhold mod højre



        //Right side
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("-45kr.", color = Color.Red)
            Spacer(modifier = Modifier.width(8.dp)) // mellem rum

            // Box med border
            Box(modifier = Modifier
                .border(2.dp, Color.Magenta)
                .padding(horizontal = 2.dp, vertical = 2.dp)
            ) {
                Text("Fjern")
            }
        }
    }

}




