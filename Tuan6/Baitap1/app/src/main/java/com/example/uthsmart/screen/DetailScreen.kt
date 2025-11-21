package com.example.uthsmart.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uthsmart.viewmodel.TaskDetailViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    navController: NavController,
    vm: TaskDetailViewModel = viewModel()
) {
    val detail = vm.detail.value
    val isLoading = vm.isLoading.value

    LaunchedEffect(id) {
        vm.loadDetail(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết công việc") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                detail == null -> Text(
                    "❌ Không tìm thấy công việc!",
                    Modifier.align(Alignment.Center)
                )

                else -> {
                    Column(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        // --- Thông tin chính ---
                        Text(detail.title, style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(4.dp))
                        Text("Trạng thái: ${detail.status}")
                        Text("Danh mục: ${detail.category}")
                        Text("Độ ưu tiên: ${detail.priority}")
                        Spacer(Modifier.height(12.dp))
                        Divider()
                        Spacer(Modifier.height(8.dp))

                        // --- Mô tả ---
                        if (detail.description.isNotEmpty()) {
                            Text("📝 Mô tả:", fontWeight = FontWeight.Bold)
                            Text(detail.description)
                            Spacer(Modifier.height(16.dp))
                        }

                        // --- Thời gian ---
                        Text("📅 Hạn chót: ${formatDate(detail.dueDate)}")
                        Text("🕓 Tạo lúc: ${formatDate(detail.createdAt)}")
                        Text("♻️ Cập nhật: ${formatDate(detail.updatedAt)}")
                        Spacer(Modifier.height(16.dp))
                        Divider()
                        Spacer(Modifier.height(8.dp))

                        // --- Subtasks ---
                        if (detail.subtasks.isNotEmpty()) {
                            Text("✅ Việc nhỏ:", fontWeight = FontWeight.Bold)
                            detail.subtasks.forEach { sub ->
                                Row(
                                    Modifier
                                        .padding(vertical = 4.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = sub.isCompleted,
                                        onCheckedChange = null
                                    )
                                    Text(sub.title)
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        // --- Attachments ---
                        if (detail.attachments.isNotEmpty()) {
                            Text("📎 Tệp đính kèm:", fontWeight = FontWeight.Bold)
                            detail.attachments.forEach { file ->
                                Text(
                                    text = "• ${file.fileName}",
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            println("📂 Mở tệp: ${file.fileUrl}")
                                        }
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        // --- Reminders ---
                        if (detail.reminders.isNotEmpty()) {
                            Text("⏰ Nhắc nhở:", fontWeight = FontWeight.Bold)
                            detail.reminders.forEach { r ->
                                Text("• ${r.type} - ${formatDate(r.time)}")
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        Divider()
                        Spacer(Modifier.height(16.dp))

                        // --- Nút xóa ---
                        Button(
                            onClick = {
                                vm.deleteTask(detail.id) {
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("XÓA CÔNG VIỆC", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }
        }
    }
}

// --------- HÀM ĐỊNH DẠNG THỜI GIAN ---------
fun formatDate(isoTime: String): String {
    return try {
        val parsed = ZonedDateTime.parse(isoTime)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale("vi", "VN"))
        parsed.format(formatter)
    } catch (e: Exception) {
        isoTime // nếu lỗi thì hiển thị nguyên văn
    }
}
