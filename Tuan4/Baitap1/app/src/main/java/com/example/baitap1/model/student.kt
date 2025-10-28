package com.example.baitap1.model

data class Student(
    val id: String,
    val name: String,
    val borrowedBooks: MutableList<Book> = mutableListOf()
)