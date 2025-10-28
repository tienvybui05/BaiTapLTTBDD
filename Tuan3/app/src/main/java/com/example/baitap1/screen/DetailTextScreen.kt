package com.example.baitap1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailTextScreen(navController: NavController) {
    Scaffold(
        // 🔹 Thanh tiêu đề
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
                        .padding(start = 30.dp)
                        .clickable { navController.navigateUp() },
                    color = Color(0xFF2196F3),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                // Tiêu đề giữa
                Text(
                    text = "Text Detail",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    ) { padding ->

        // 🔹 Nội dung ví dụ văn bản
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append("The quick ")
                    }
                    withStyle(SpanStyle(color = Color(0xFF795548), fontWeight = FontWeight.Bold)) {
                        append("Brown ")
                    }
                    append("fox j")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("u")
                    }
                    append("mps over the ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("lazy dog")
                    }
                },
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Example of styled text using Jetpack Compose",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailTextScreen() {
    val navController = rememberNavController()
    DetailTextScreen(navController = navController)
}
