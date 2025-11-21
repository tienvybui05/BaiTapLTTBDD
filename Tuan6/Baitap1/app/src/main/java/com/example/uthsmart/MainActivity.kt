package com.example.uthsmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.uthsmart.datastore.ThemeDataStore
import com.example.uthsmart.navigation.AppNav
import com.example.uthsmart.viewmodel.ThemeViewModel
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.uthsmart.navigation.AppNav


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 tạo DataStore + ViewModel
        val dataStore = ThemeDataStore(this)
        val vm = ThemeViewModel(dataStore)

        setContent {
            val theme by vm.theme.collectAsState()
            val navController = rememberNavController()

            // đổi màu giao diện tổng thể theo theme
            val colorScheme = when (theme) {
                "Blue" -> lightColorScheme(primary = Color(0xFF2196F3))
                "Green" -> lightColorScheme(primary = Color(0xFF4CAF50))
                "Red" -> lightColorScheme(primary = Color(0xFFF44336))
                else -> lightColorScheme()
            }

            MaterialTheme(colorScheme = colorScheme) {
                // ✅ TRUYỀN ĐỦ THAM SỐ VÀO ĐÂY
                AppNav(navController = navController, vm = vm)
            }
        }
    }
}
