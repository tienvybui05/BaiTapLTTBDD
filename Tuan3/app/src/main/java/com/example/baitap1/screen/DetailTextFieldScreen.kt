package com.example.baitap1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailTextFieldScreen(navController: NavController) {
    Scaffold(
        // Thanh tiêu đề
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp)
            ) {
                // Nút quay lại
                Text(
                    text = "<",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                        .clickable { navController.navigateUp() },
                    color = Color(0xFF2196F3),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // Tiêu đề trang
                Text(
                    text = "TextField",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    ) { padding ->

        // Nội dung trang
        var text by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ô nhập dữ liệu
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Text động cập nhật theo nội dung
            Text(
                text = if (text.isEmpty())
                    "Tự động cập nhật dữ liệu theo textfield"
                else
                    "Bạn vừa nhập: $text",
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailTextFieldScreen() {
    val navController = rememberNavController()
    DetailTextFieldScreen(navController)
}
