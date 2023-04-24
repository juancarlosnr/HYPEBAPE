package com.example.hypebape

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypebape.data.auth.AuthRepository
import com.example.hypebape.presentation.login_screen.SignInState
import com.example.hypebape.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _navegarALogin = MutableLiveData<Boolean>()
    val navegarALogin = _navegarALogin
    val _getUserState = Channel<SignInState>()
    val getUserState = _getUserState.receiveAsFlow()
    var splashLoading by mutableStateOf(true)
        private set

    fun checkAuthentication() {
        viewModelScope.launch {
            delay(3000)
            splashLoading = false

        }
    }

    fun getOnBoarding(context: Context) {
        viewModelScope.launch {
            _navegarALogin.value =
                context.dataStore.data.first()[booleanPreferencesKey("on_boarding")] ?: false
            Log.d("onBoarding", "valor de $navegarALogin")
        }


    }

    suspend fun getUser() {
        repository.getUser().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _getUserState.send(SignInState(isSuccess = result.data))
                }
                is Resource.Loading -> {
                    _getUserState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _getUserState.send(SignInState(isError = result.message))
                }
            }
        }
    }

}