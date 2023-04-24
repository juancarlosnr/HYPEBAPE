package com.example.hypebape.data.profile

import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
     fun getData(): Flow<Resource<User>>
     fun getFavorites():Flow<Resource<List<Sneaker>>>
     fun deleteFavorite(idSneaker:Int)
}