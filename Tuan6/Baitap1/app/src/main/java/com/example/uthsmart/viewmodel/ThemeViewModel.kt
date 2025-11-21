package com.example.uthsmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmart.datastore.ThemeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val dataStore: ThemeDataStore) : ViewModel() {

    val theme = dataStore.getTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, "Light")

    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            dataStore.saveTheme(newTheme)
        }
    }
}
