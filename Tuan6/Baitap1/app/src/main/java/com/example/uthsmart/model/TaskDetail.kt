package com.example.uthsmart.model

data class TaskDetail(
    val id: Int,
    val title: String,
    val status: String,
    val category: String,
    val subtasks: List<SubTask>?,
    val attachments: List<Attachment>?,
    val reminders: List<Reminder>?
)

data class SubTask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

data class Attachment(
    val id: Int,
    val fileName: String,
    val fileUrl: String
)

data class Reminder(
    val id: Int,
    val time: String,
    val type: String
)
