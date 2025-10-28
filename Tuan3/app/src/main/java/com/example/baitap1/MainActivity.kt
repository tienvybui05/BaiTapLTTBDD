package com.example.baitap1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.baitap1.navigation.AppNavGraph
import com.example.baitap1.ui.theme.Baitap1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Baitap1Theme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
