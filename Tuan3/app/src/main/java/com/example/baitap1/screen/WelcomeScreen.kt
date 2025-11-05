package com.example.baitap1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.baitap1.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFFDFBFF) // nền trắng sáng
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            // 🟢 Logo
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 32.dp)
            )

            // 🟣 Tiêu đề
            Text(
                text = "Jetpack Compose",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Mô tả
            Text(
                text = "Jetpack Compose là bộ công cụ UI hiện đại giúp xây dựng ứng dụng Android dễ dàng và mạnh mẽ.",
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 🔵 Nút “I’m ready”
            Button(
                onClick = { navController.navigate("list") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6750A4)
                ),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "I'm ready",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewWelcomeScreen() {
    // NavController giả lập để preview
    val fakeNavController = rememberNavController()
    WelcomeScreen(navController = fakeNavController)
}
