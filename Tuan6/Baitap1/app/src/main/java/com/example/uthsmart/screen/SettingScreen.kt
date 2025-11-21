package com.example.uthsmart.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uthsmart.viewmodel.ThemeViewModel
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController, vm: ThemeViewModel) {
    val theme by vm.theme.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Setting") },
            navigationIcon = {   // 👈 thêm nút quay lại
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
        ) {
            Text("Setting", style = MaterialTheme.typography.titleLarge)

            Text("Lựa chọn màu", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { vm.setTheme("Blue") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Blue
                ) {
                    Text("Blue", color = Color.White)
                }

                Button(
                    onClick = { vm.setTheme("Green") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green
                ) {
                    Text("Green", color = Color.White)
                }

                Button(
                    onClick = { vm.setTheme("Red") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)) // Red
                ) {
                    Text("Red", color = Color.White)
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Màu hiện tại: $theme")

            Spacer(Modifier.height(24.dp))
            Button(onClick = { navController.navigate("detailColor") }) {
                Text("Xem kết quả")
            }
        }
    }
}
