package com.example.budgetapp_grouptwo.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.budgetapp_grouptwo.R






@Composable
fun NavigationMenu(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(top=32.dp),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NavItem(
            label = "Mål",
            icon = Icons.Rounded.Flag,
            selected = currentRoute == "goals"
        ) { navController.navigate("goals") }

        NavItem(
            label = "Hjem",
            icon = Icons.Rounded.Home,
            selected = currentRoute == "home"
        ) { navController.navigate("home") }

        NavItem(
            label = "Seneste",
            icon = Icons.Rounded.History,
            selected = currentRoute == "recentDetails"
        ) { navController.navigate("recentDetails") }
    }
}

@Composable
fun NavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Color(0xFFE0E0E0) else Color.Transparent
    val contentColor = if (selected) Color.Black else Color.Gray

    Column(
        modifier = Modifier
            .widthIn(min = 100.dp, max = 150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(35.dp)
        )

        Text(
            text = label,
            color = contentColor,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1
        )
    }
}



