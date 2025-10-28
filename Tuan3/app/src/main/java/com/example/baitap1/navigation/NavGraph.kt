package com.example.baitap1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baitap1.screen.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    // NavHost chứa tất cả các "route" (màn hình)
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(route = "welcome") { WelcomeScreen(navController) }
        composable(route = "list") { ComponentsListScreen(navController) }
        composable(route = "detail_text") { DetailTextScreen(navController) }
        composable(route = "detail_image") { DetailImageScreen(navController) }
        composable(route = "detail_textfield") { DetailTextFieldScreen(navController) }
        composable(route = "detail_password") { DetailPasswordFieldScreen(navController) }
        composable(route = "detail_row") { DetailRowLayoutScreen(navController) }
    }
}
