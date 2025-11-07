package com.example.uth.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ForgetPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
    var verifyCode by mutableStateOf("")
    var newPassword by mutableStateOf("")
}