package com.example.budgetapp_grouptwo.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun NavigationMenu(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavItem(
                label = "Hjem",
                selected = currentRoute == "home"
            ) { navController.navigate("home") }

            NavItem(
                label = "Mål",
                selected = currentRoute == "goals"
            ) { navController.navigate("goals") }

            NavItem(
                label = "Seneste",
                selected = currentRoute == "recent"
            ) { navController.navigate("recent") }
        }

    }
}

@Composable
fun NavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Color.DarkGray else Color.LightGray
    val alpha = if (selected) 1f else 0.5f

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor.copy(alpha = alpha))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color.White else Color.Black.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun simpleNavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Color.DarkGray else Color.LightGray
    val alpha = if (selected) 1f else 0.5f

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor.copy(alpha = alpha))
            .clickable { onClick() }
            .padding(horizontal = 2.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color.White else Color.Black.copy(alpha = 0.7f)
        )
    }
}




