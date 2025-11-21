package com.example.uthsmart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uthsmart.viewmodel.ThemeViewModel
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailColorScreen(navController: NavController, vm: ThemeViewModel) {
    val theme by vm.theme.collectAsState()

    val bgColor = when (theme) {
        "Blue" -> Color(0xFF2196F3)
        "Green" -> Color(0xFF4CAF50)
        "Red" -> Color(0xFFF44336)
        else -> Color(0xFFFFFFFF)
    }

    val textColor = if (theme == "Red") Color.White else Color.Black

    Scaffold(
        topBar = { TopAppBar(title = { Text("Chi tiết màu") },
            ) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Màu hiện tại: $theme",
                    color = textColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Đây là giao diện ví dụ với màu $theme",
                    color = textColor
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = { navController.navigate("setting") }) {
                    Text("Setting")
                }
            }
        }
    }
}
