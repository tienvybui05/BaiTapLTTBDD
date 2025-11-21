package com.example.uthsmart.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmart.model.Task
import com.example.uthsmart.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val repo = TaskRepository()
    var tasks = mutableStateOf<List<Task>>(emptyList())
    var isLoading = mutableStateOf(true)

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            isLoading.value = true
            tasks.value = repo.fetchTasks()
            isLoading.value = false
        }
    }

    // ✅ Thêm hàm này
    fun deleteTask(id: Int) {
        viewModelScope.launch {
            try {
                repo.deleteTask(id)
                loadTasks() // refresh lại danh sách
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
