package com.example.baitap1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ComponentsListScreen(navController: NavController) {
    Scaffold(
        // 🔹 Thanh tiêu đề trên cùng
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "UI Components List",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    ) { padding ->

        // 🔹 Phần nội dung chính
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- DISPLAY ---
            SectionHeader("Display")
            ComponentCard(
                title = "Text",
                description = "Displays text"
            ) { navController.navigate("detail_text") }

            ComponentCard(
                title = "Image",
                description = "Displays an image"
            ) { navController.navigate("detail_image") }

            // --- INPUT ---
            SectionHeader("Input")
            ComponentCard(
                title = "TextField",
                description = "Input field for text"
            ) { navController.navigate("detail_textfield") }

            ComponentCard(
                title = "PasswordField",
                description = "Input field for passwords"
            ) {}

            // --- LAYOUT ---
            SectionHeader("Layout")
            ComponentCard(
                title = "Column",
                description = "Arranges elements vertically"
            ) {}

            ComponentCard(
                title = "Row",
                description = "Arranges elements horizontally"
            ) {}
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun ComponentCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB3E5FC) // xanh nhạt
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewComponentsListScreen() {
    val navController = rememberNavController()
    ComponentsListScreen(navController = navController)
}
