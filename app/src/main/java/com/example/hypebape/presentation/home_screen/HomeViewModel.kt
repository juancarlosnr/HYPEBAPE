package com.example.hypebape.presentation.home_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypebape.data.home.Advertising
import com.example.hypebape.data.home.Brand
import com.example.hypebape.data.home.HomeRepository
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {


    private var _sneakers = MutableLiveData<List<Sneaker>?>()
    val sneakersList = _sneakers

    private var _brands = MutableLiveData<List<Brand>?>()
    val brandsList = _brands

    private var _advertising = MutableLiveData<List<Advertising>?>()
    val advertising = _advertising

    private var _favorites = MutableLiveData<List<Int>?>()
    val favorites = _favorites

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isSearchingByBrand = MutableStateFlow(false)
    val isSearchingByBrand = _isSearchingByBrand.asStateFlow()


    private val sneakersState = MutableStateFlow(mutableListOf<Sneaker>())

    /*
    val sneakers2 = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(sneakersState) { text, sneakers ->
            if(text.isBlank()) {
                sneakers
            } else {
                delay(2000L)
                sneakers?.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            sneakersState.value
        )
*/
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getSneakers() = viewModelScope.launch {
        repository.getSneakers().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _sneakers.postValue(result.data)
                    sneakersState.value = result.data!!.toMutableList()
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }

    fun getSneakersByBrands(brand: String) = viewModelScope.launch {
        if (brand == "allproducts") {
            getSneakers()
        } else {
            repository.getSneakersByBrands(brand).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _sneakers.value = result.data
                        Log.d("valorsneakers", _sneakers.value.toString())
                        _isSearchingByBrand.value = true
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }
                }
            }
        }

    }

    fun getBrands() = viewModelScope.launch {
        repository.getBrands().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _brands.postValue(result.data)
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }

    fun getAdvertising() = viewModelScope.launch {
        repository.getAdvertising().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _advertising.postValue(result.data)
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }

    fun setOrDeleteFavorite(idSneaker: Int) = viewModelScope.launch {
        repository.setOrDeleteFavorite(idSneaker)
    }

    fun getFavorites() = viewModelScope.launch {
        repository.getFavorites().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _favorites.postValue(result.data)
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }
            }

        }
    }


}
