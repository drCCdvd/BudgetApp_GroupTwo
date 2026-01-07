package com.example.budgetapp_grouptwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgetapp_grouptwo.ui.theme.BudgetApp_GroupTwoTheme
import com.example.budgetapp_grouptwo.ui.screens.FixedEntryScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetApp_GroupTwoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // 1. Thêm innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) { // 2. Bọc Box để tránh bị che bởi thanh trạng thái
                        FixedEntryScreen(
                            onBack = { finish() }
                        )
                    }
                }

            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        BudgetApp_GroupTwoTheme {
            Greeting("Android")
        }
    }
}