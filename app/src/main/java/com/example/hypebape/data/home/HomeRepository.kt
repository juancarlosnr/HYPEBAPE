package com.example.hypebape.data.home

import com.example.hypebape.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getSneakers(): Flow<Resource<List<Sneaker>>>
    fun getBrands(): Flow<Resource<List<Brand>>>
    fun setFavorites(idSneaker:Int)
    fun getFavorites():Flow<Resource<List<Int>>>
    suspend fun setOrDeleteFavorite(idSneaker: Int)
    fun deleteFavorite(idSneaker:Int)
    fun getSneakersByBrands(brand:String):Flow<Resource<List<Sneaker>>>
    fun getAdvertising():Flow<Resource<List<Advertising>>>
}