package com.example.baitap1.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baitap1.navigation.Screen
import com.example.baitap1.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    var studentName by remember { mutableStateOf("") }
    var selectedStudent by remember { mutableStateOf<com.example.baitap1.model.Student?>(null) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var expanded by remember { mutableStateOf(false) }
    var showBorrowBookDialog by remember { mutableStateOf(false) }

    // Theo dõi update trigger để force recomposition
    val updateTrigger by viewModel.updateTrigger

    // Danh sách sinh viên được lọc theo input
    val filteredStudents = remember(studentName, viewModel.students.size) {
        viewModel.searchStudents(studentName)
    }

    // Hiển thị thông báo Snackbar
    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar(snackbarMessage)
            showSnackbar = false
        }
    }

    // Dialog CHỌN SÁCH ĐỂ MƯỢN (không phải thêm sách mới)
    if (showBorrowBookDialog && selectedStudent != null) {
        AlertDialog(
            onDismissRequest = { showBorrowBookDialog = false },
            title = { Text("Chọn sách để mượn") },
            text = {
                // Hiển thị danh sách sách có sẵn (chưa ai mượn)
                val availableBooks = viewModel.books.filter { !it.isBorrowed }

                if (availableBooks.isEmpty()) {
                    Text(
                        "Không có sách nào để mượn.\nTất cả sách đã được mượn hết.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        items(availableBooks) { book ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        val success = viewModel.borrowBook(selectedStudent!!, book)
                                        if (success) {
                                            snackbarMessage = "✅ Đã mượn sách: ${book.title}"
                                            showSnackbar = true
                                            showBorrowBookDialog = false
                                        }
                                    },
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF5F5F5)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "📚",
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        book.title,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showBorrowBookDialog = false }) {
                    Text("Đóng")
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Hệ thống",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Quản lý Thư viện",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = Screen.Management
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Student Selection Section
            Text(
                "Sinh viên",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row chứa Dropdown và Nút Thay đổi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ExposedDropdownMenu for student selection
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        // Chỉ mở dropdown khi click vào icon, KHÔNG mở khi đang gõ
                        if (studentName.isNotBlank()) {
                            expanded = it
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = studentName,
                        onValueChange = { newValue ->
                            studentName = newValue
                            // Chỉ tự động mở dropdown khi ĐANG GÕ (không phải xóa)
                            if (newValue.length > studentName.length) {
                                expanded = true
                            }
                            // Khi xóa hết text, đóng dropdown
                            if (newValue.isEmpty()) {
                                expanded = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        placeholder = { Text("Gõ tên hoặc chọn sinh viên...") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1976D2),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded && filteredStudents.isNotEmpty(),
                        onDismissRequest = { expanded = false }
                    ) {
                        filteredStudents.forEach { student ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(student.name, fontWeight = FontWeight.Medium)
                                        Text(
                                            "Đang mượn: ${student.borrowedBooks.size} sách",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                },
                                onClick = {
                                    studentName = student.name
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Nút Thay đổi
                Button(
                    onClick = {
                        if (studentName.isNotBlank()) {
                            val foundStudent = viewModel.findStudentByName(studentName)
                            if (foundStudent != null) {
                                selectedStudent = foundStudent
                                snackbarMessage = "✅ Đã chọn sinh viên: ${foundStudent.name}"
                                showSnackbar = true
                            } else {
                                selectedStudent = null
                                snackbarMessage = "❌ Không tìm thấy sinh viên: $studentName"
                                showSnackbar = true
                            }
                        } else {
                            snackbarMessage = "⚠️ Vui lòng nhập tên sinh viên"
                            showSnackbar = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Thay đổi", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Book List Section
            Text(
                "Danh sách sách",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CHỈ HIỂN THỊ SÁCH ĐÃ MƯỢN CỦA SINH VIÊN (KHÔNG CÓ TEXT HƯỚNG DẪN)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                // Force recomposition when updateTrigger changes
                key(updateTrigger) {
                    when {
                        // Chưa chọn sinh viên - KHÔNG HIỂN THỊ GÌ (hoặc để trống)
                        selectedStudent == null -> {
                            // Để trống - không hiển thị thông báo
                        }
                        // Đã chọn sinh viên NHƯNG chưa mượn sách nào
                        selectedStudent!!.borrowedBooks.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        // Đã chọn sinh viên VÀ đã mượn sách
                        else -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(selectedStudent!!.borrowedBooks.toList()) { book ->
                                    BorrowedBookItem(
                                        bookTitle = book.title,
                                        onReturn = {
                                            viewModel.returnBook(selectedStudent!!, book)
                                            snackbarMessage = "✅ Đã trả sách: ${book.title}"
                                            showSnackbar = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NÚT THÊM - Mở dialog chọn sách để mượn
            Button(
                onClick = {
                    if (selectedStudent != null) {
                        showBorrowBookDialog = true
                    } else {
                        snackbarMessage = "⚠️ Vui lòng chọn sinh viên trước"
                        showSnackbar = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Thêm",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Component hiển thị sách đã mượn (có checkbox đỏ)
@Composable
fun BorrowedBookItem(
    bookTitle: String,
    onReturn: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onReturn() },
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = { onReturn() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD32F2F)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(bookTitle, fontSize = 16.sp)
        }
    }
}

@Composable
fun BookItem(
    bookTitle: String,
    isBorrowed: Boolean,
    onClick: () -> Unit,
    isDisabled: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isDisabled) { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = if (isDisabled) Color(0xFFBDBDBD) else Color.White
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isBorrowed,
                onCheckedChange = if (!isDisabled) { { onClick() } } else null,
                enabled = !isDisabled,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD32F2F),
                    disabledCheckedColor = Color.Gray,
                    disabledUncheckedColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    bookTitle,
                    fontSize = 16.sp,
                    color = if (isDisabled) Color.Gray else Color.Black
                )
                if (isDisabled) {
                    Text(
                        "Đã được mượn bởi người khác",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentScreen: Screen
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Text("🏠", fontSize = 24.sp) },
            label = { Text("Quản lý", fontSize = 12.sp) },
            selected = currentScreen == Screen.Management,
            onClick = { navController.navigate(Screen.Management.route) }
        )
        NavigationBarItem(
            icon = { Text("📚", fontSize = 24.sp) },
            label = { Text("DS Sách", fontSize = 12.sp) },
            selected = currentScreen == Screen.BookList,
            onClick = { navController.navigate(Screen.BookList.route) }
        )
        NavigationBarItem(
            icon = { Text("👤", fontSize = 24.sp) },
            label = { Text("Sinh viên", fontSize = 12.sp) },
            selected = currentScreen == Screen.StudentList,
            onClick = { navController.navigate(Screen.StudentList.route) }
        )
    }
}