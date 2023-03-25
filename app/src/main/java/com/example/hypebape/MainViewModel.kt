package com.example.hypebape

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
     var splashLoading by mutableStateOf(true)
        private set

    fun checkAuthentication(){
        viewModelScope.launch {
            delay(5000)
            splashLoading = false
        }
    }
}