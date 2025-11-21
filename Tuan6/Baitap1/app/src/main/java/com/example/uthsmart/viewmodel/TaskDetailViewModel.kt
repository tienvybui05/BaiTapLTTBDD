package com.example.uthsmart.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// ----- Model con -----
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

// ----- Model chính -----
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val subtasks: List<SubTask>,
    val attachments: List<Attachment>,
    val reminders: List<Reminder>
)

class TaskDetailViewModel : ViewModel() {

    var detail = mutableStateOf<Task?>(null)
        private set

    var isLoading = mutableStateOf(true)
        private set

    // ✅ Gọi API thật
    fun loadDetail(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val task = fetchTaskDetailFromAPI(id)
            detail.value = task
            isLoading.value = false
        }
    }

    // ✅ Gọi API DELETE
    fun deleteTask(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = deleteTaskFromAPI(id)
            if (result) {
                println("✅ Đã xóa task có id = $id trên server")
                onSuccess()
            } else {
                println("❌ Xóa task thất bại hoặc API không phản hồi")
            }
        }
    }

    // ✅ Hàm đọc dữ liệu thật từ API (có data[])
    private suspend fun fetchTaskDetailFromAPI(id: Int): Task? = withContext(Dispatchers.IO) {
        try {
            val jsonText = URL("https://amock.io/api/researchUTH/tasks").readText()
            val root = JSONObject(jsonText)
            val dataArray = root.optJSONArray("data") ?: return@withContext null

            // tìm đúng task theo id
            for (i in 0 until dataArray.length()) {
                val obj = dataArray.getJSONObject(i)
                if (obj.optInt("id") == id) {

                    // Parse subtasks
                    val subtasks = parseSubTasks(obj.optJSONArray("subtasks"))

                    // Parse attachments
                    val attachments = parseAttachments(obj.optJSONArray("attachments"))

                    // Parse reminders
                    val reminders = parseReminders(obj.optJSONArray("reminders"))

                    // Tạo Task hoàn chỉnh
                    return@withContext Task(
                        id = obj.optInt("id"),
                        title = obj.optString("title"),
                        description = obj.optString("description"),
                        status = obj.optString("status"),
                        priority = obj.optString("priority"),
                        category = obj.optString("category"),
                        dueDate = obj.optString("dueDate"),
                        createdAt = obj.optString("createdAt"),
                        updatedAt = obj.optString("updatedAt"),
                        subtasks = subtasks,
                        attachments = attachments,
                        reminders = reminders
                    )
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // --------- PARSE HỖ TRỢ ---------
    private fun parseSubTasks(arr: JSONArray?): List<SubTask> {
        val list = mutableListOf<SubTask>()
        if (arr != null) {
            for (i in 0 until arr.length()) {
                val s = arr.getJSONObject(i)
                list.add(
                    SubTask(
                        id = s.optInt("id"),
                        title = s.optString("title"),
                        isCompleted = s.optBoolean("isCompleted")
                    )
                )
            }
        }
        return list
    }

    private fun parseAttachments(arr: JSONArray?): List<Attachment> {
        val list = mutableListOf<Attachment>()
        if (arr != null) {
            for (i in 0 until arr.length()) {
                val a = arr.getJSONObject(i)
                list.add(
                    Attachment(
                        id = a.optInt("id"),
                        fileName = a.optString("fileName"),
                        fileUrl = a.optString("fileUrl")
                    )
                )
            }
        }
        return list
    }

    private fun parseReminders(arr: JSONArray?): List<Reminder> {
        val list = mutableListOf<Reminder>()
        if (arr != null) {
            for (i in 0 until arr.length()) {
                val r = arr.getJSONObject(i)
                list.add(
                    Reminder(
                        id = r.optInt("id"),
                        time = r.optString("time"),
                        type = r.optString("type")
                    )
                )
            }
        }
        return list
    }

    // ✅ Hàm DELETE
    private suspend fun deleteTaskFromAPI(id: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://amock.io/api/researchUTH/task/$id")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "DELETE"
            conn.connect()
            val success = conn.responseCode in 200..299
            conn.disconnect()
            success
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
