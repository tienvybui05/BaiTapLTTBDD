package com.example.baitap1.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
fun BookListScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    var newBookTitle by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var bookToDelete by remember { mutableStateOf<com.example.baitap1.model.Book?>(null) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    // Hiển thị thông báo Snackbar
    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar(snackbarMessage)
            showSnackbar = false
        }
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
                currentScreen = Screen.BookList
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Danh sách sách",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Books List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.books) { book ->
                    BookListItem(
                        book = book,
                        onDelete = {
                            bookToDelete = book
                            showDeleteDialog = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add Book Button
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Thêm Sách", fontSize = 16.sp)
            }
        }
    }

    // Add Book Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Thêm sách mới") },
            text = {
                OutlinedTextField(
                    value = newBookTitle,
                    onValueChange = { newBookTitle = it },
                    label = { Text("Tên sách") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newBookTitle.isNotBlank()) {
                            viewModel.addBook(newBookTitle)
                            snackbarMessage = "✅ Đã thêm sách: $newBookTitle"
                            showSnackbar = true
                            newBookTitle = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Thêm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && bookToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = { Text("⚠️", fontSize = 48.sp) },
            title = { Text("Xác nhận xóa sách") },
            text = {
                Column {
                    Text("Bạn có chắc chắn muốn xóa sách:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "\"${bookToDelete!!.title}\"",
                        fontWeight = FontWeight.Bold
                    )
                    if (bookToDelete!!.isBorrowed) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "⚠️ Sách này đang được mượn!",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val deletedTitle = bookToDelete!!.title
                        viewModel.removeBook(bookToDelete!!)
                        snackbarMessage = "🗑️ Đã xóa sách: $deletedTitle"
                        showSnackbar = true
                        showDeleteDialog = false
                        bookToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    bookToDelete = null
                }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun BookListItem(
    book: com.example.baitap1.model.Book,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (book.isBorrowed) "Đã mượn" else "Có sẵn",
                    fontSize = 14.sp,
                    color = if (book.isBorrowed) Color.Red else Color.Green
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Xóa sách",
                    tint = Color.Red
                )
            }
        }
    }
}
