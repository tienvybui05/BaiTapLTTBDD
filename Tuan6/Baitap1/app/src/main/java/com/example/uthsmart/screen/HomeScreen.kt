package com.example.uthsmart.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uthsmart.viewmodel.TaskListViewModel
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(navController: NavController, vm: TaskListViewModel = viewModel()) {
    val tasks by vm.tasks
    val isLoading by vm.isLoading

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task List") }) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                tasks.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No Tasks Yet 😴")
                }
                else -> LazyColumn(Modifier.padding(16.dp)) {
                    items(tasks) { task ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { navController.navigate("detail/${task.id}") },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(task.title, style = MaterialTheme.typography.titleMedium)
                                Text("Status: ${task.status}")
                                Text("Date: ${task.date}")
                            }
                        }
                    }
                }
            }
        }
    }
}
