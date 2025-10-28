package com.example.baitap1.model

data class Book(
    val id: String,
    val title: String,
    var isBorrowed: Boolean = false
)
