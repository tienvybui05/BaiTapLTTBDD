package com.example.uthsmart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uthsmart.screen.*
import com.example.uthsmart.viewmodel.ThemeViewModel

@Composable
fun AppNav(navController: NavHostController, vm: ThemeViewModel) {
    NavHost(
        navController = navController,
        startDestination = "detailColor"
    ) {

        // Home API
        composable("home") {
            HomeScreen(navController)
        }

        // Setting
        composable("setting") {
            SettingScreen(navController, vm)
        }

        // Màu theme
        composable("detailColor") {
            DetailColorScreen(navController, vm)
        }

        // Detail có ID
        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(
                id = id,
                navController = navController
            )
        }
    }
}
