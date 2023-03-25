package com.example.hypebape

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainViewModel(context: Context) : ViewModel() {
    var contexto = context
    private val _navegarALogin = MutableLiveData<Boolean>()
    val navegarALogin = _navegarALogin
    var splashLoading by mutableStateOf(true)
        private set

    fun checkAuthentication() {
        viewModelScope.launch {
            delay(5000)
            splashLoading = false

        }
    }
    fun getOnBoarding() {
        viewModelScope.launch {
            _navegarALogin.value = contexto.dataStore.data.first()[booleanPreferencesKey("on_boarding")] ?: false
            Log.d("onBoarding", "valor de $navegarALogin")
        }


    }
    /*fun getOnBoarding() {
        viewModelScope.launch {
            val onBoardingStatus =
                contexto.dataStore.data.first()[booleanPreferencesKey("on_boarding")] ?: false
            Log.d("onBoardingStatus", onBoardingStatus.toString())
            navegarALogin = onBoardingStatus
        }

    }*/
}