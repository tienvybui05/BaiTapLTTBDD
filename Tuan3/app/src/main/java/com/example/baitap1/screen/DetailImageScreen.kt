package com.example.baitap1.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.baitap1.R

@Composable
fun DetailImageScreen(navController: NavController) {
    Scaffold(
        // 🔹 Thanh tiêu đề trên cùng
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

                // Tiêu đề
                Text(
                    text = "Images",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    ) { padding ->
        // 🔹 Nội dung chính có scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Ảnh từ link
            AsyncImage(
                model = "https://cdn.vietnambiz.vn/171464876016439296/2020/5/23/photo-1590201085601-1590201085824333251774.jpg",
                contentDescription = "Logo from URL",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(vertical = 10.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "https://giaothongvantaihochm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png",
                color = Color(0xFF2196F3),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Ảnh trong máy (local)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Local Image Example",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(vertical = 10.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "In app",
                color = Color.Gray,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailImageScreen() {
    val navController = rememberNavController()
    DetailImageScreen(navController = navController)
}
