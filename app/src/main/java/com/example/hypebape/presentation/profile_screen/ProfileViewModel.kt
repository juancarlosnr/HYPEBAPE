package com.example.hypebape.presentation.profile_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypebape.data.auth.AuthRepository
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.data.profile.ProfileRepository
import com.example.hypebape.data.profile.User
import com.example.hypebape.presentation.login_screen.SignInState
import com.example.hypebape.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private var _user = MutableLiveData<User>()
    val userData = _user

    private var _sneakers = MutableLiveData<List<Sneaker>?>()
    val listSneakers = _sneakers

    fun getDataUser() = viewModelScope.launch {
        repository.getData().collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        Log.d("usuariofoto", it.urlPhoto
                        )
                    }
                    _user.postValue(result.data?.let {
                        User(
                            result.data.id,
                            result.data.name,
                            result.data.urlPhoto,
                            result.data.favorites
                        )
                    })
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }

        }
    }
    fun getFavoritesSneakers() = viewModelScope.launch {
        repository.getFavorites().collect { result ->
            when (result) {
                is Resource.Success -> {
                   _sneakers.postValue(result.data)
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }

        }
    }
    fun deleteFavorite(idSneaker: Int) = viewModelScope.launch {
        repository.deleteFavorite(idSneaker)
        getFavoritesSneakers()
    }

}