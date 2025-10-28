package com.example.baitap1.navigation

sealed class Screen(val route: String) {
    object Management : Screen("management")
    object BookList : Screen("book_list")
    object StudentList : Screen("student_list")
}