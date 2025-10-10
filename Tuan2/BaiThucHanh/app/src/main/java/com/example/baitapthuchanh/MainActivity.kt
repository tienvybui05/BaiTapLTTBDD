package com.example.baitapthuchanh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitapthuchanh.ui.theme.BaiTapThucHanhTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaiTapThucHanhTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ThucHanh02Screen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/* -------------------- UI chính -------------------- */
@Composable
fun ThucHanh02Screen(modifier: Modifier = Modifier) {
    var input by rememberSaveable { mutableStateOf("") }
    var numbers by remember { mutableStateOf<List<Int>>(emptyList()) }
    var error by remember { mutableStateOf("") }
    val focus = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Thực hành 02", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        InputSection(
            input = input,
            onInputChange = { input = it },
            onGenerateClick = {
                val result = validateAndGenerate(input)
                if (result.isValid) {
                    numbers = result.list
                    error = ""
                } else {
                    numbers = emptyList()
                    error = result.message
                }
                focus.clearFocus()
            }
        )

        Spacer(Modifier.height(16.dp))
        ErrorMessage(error)
        NumberList(numbers)
    }
}

/* -------------------- Hàm con/tách riêng -------------------- */

// 1) Nhập & nút Tạo
@Composable
fun InputSection(
    input: String,
    onInputChange: (String) -> Unit,
    onGenerateClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            label = { Text("Nhập vào số lượng") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = onGenerateClick) { Text("Tạo") }
    }
}

// 2) Validate & sinh danh sách
data class ValidationResult(
    val isValid: Boolean,
    val list: List<Int> = emptyList(),
    val message: String = ""
)

fun validateAndGenerate(input: String): ValidationResult {
    val n = input.trim().toIntOrNull()
        ?: return ValidationResult(false, message = "Dữ liệu bạn nhập không hợp lệ")
    if (n <= 0) return ValidationResult(false, message = "Vui lòng nhập số > 0")
    return ValidationResult(true, list = (1..n).toList())
}

// 3) Hiển thị lỗi
@Composable
fun ErrorMessage(message: String) {
    if (message.isNotBlank()) {
        Text(
            text = message,
            color = Color(0xFFD32F2F),
            fontWeight = FontWeight.Bold
        )
    }
}

// 4) Hiển thị danh sách số
@Composable
fun NumberList(numbers: List<Int>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(numbers) { num ->
            Button(
                onClick = { /* TODO: xử lý khi bấm số nếu cần */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                )
            ) {
                Text(num.toString())
            }
        }
    }
}

/* -------------------- Preview -------------------- */
@Preview(showBackground = true)
@Composable
fun ThucHanh02Preview() {
    BaiTapThucHanhTheme { ThucHanh02Screen() }
}
