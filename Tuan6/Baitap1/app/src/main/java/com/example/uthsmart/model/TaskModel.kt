package com.example.uthsmart.model

// Dữ liệu thật trong "data": []


// Response bọc ngoài { "data": [...] }
data class TaskResponse(
    val data: List<Task>
)

// Nếu API chi tiết task khác, em có thể giữ lại như cũ

