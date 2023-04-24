package com.example.hypebape.data.detail

import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun getDetailSneaker(idSneaker:Int):Flow<Resource<Sneaker>>
}