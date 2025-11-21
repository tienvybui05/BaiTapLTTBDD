package com.example.uthsmart.repository

import com.example.uthsmart.model.Task
import com.example.uthsmart.model.TaskDetail
import com.example.uthsmart.network.RetrofitClient

class TaskRepository {

    // ✅ Lấy danh sách task (trả về List<Task>)
    suspend fun fetchTasks(): List<Task> {
        return try {
            val response = RetrofitClient.api.getTasks()
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // ✅ Lấy chi tiết task theo ID
    suspend fun fetchTaskDetail(id: Int): TaskDetail? {
        return try {
            val response = RetrofitClient.api.getTaskDetail(id)
            if (response.isSuccessful) {
                response.body()
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ✅ Xóa task theo ID
    suspend fun deleteTask(id: Int) {
        try {
            RetrofitClient.api.deleteTask(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
