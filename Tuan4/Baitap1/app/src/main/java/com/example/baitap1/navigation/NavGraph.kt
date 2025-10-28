package com.example.baitap1.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baitap1.screen.BookListScreen
import com.example.baitap1.screen.ManagementScreen
import com.example.baitap1.navigation.StudentListScreen
import com.example.baitap1.viewmodel.LibraryViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: LibraryViewModel
) {
    NavHost(
        navController = navController,
        // Show the student list first so the app opens on the interactive list screen
        startDestination = Screen.StudentList.route
    ) {
        composable(Screen.Management.route) {
            ManagementScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Screen.BookList.route) {
            BookListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Screen.StudentList.route) {
            StudentListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}