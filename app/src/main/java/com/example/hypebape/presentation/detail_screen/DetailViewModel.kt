package com.example.hypebape.presentation.detail_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypebape.data.detail.DetailRepository
import com.example.hypebape.data.detail.DetailRepositoryImpl
import com.example.hypebape.data.home.HomeRepository
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private var _sneaker = MutableLiveData<Sneaker?>()
    val sneaker = _sneaker

    fun getDetailSneaker(idSneaker:Int) = viewModelScope.launch {
        repository.getDetailSneaker(idSneaker).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _sneaker.postValue(result.data)
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }
}