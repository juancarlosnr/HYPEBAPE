package com.example.hypebape.data.home

import android.util.Log
import java.util.Locale

data class Sneaker(
    val brand: String = "",
    val id: Int = 0,
    val name: String = "",
    val urlphoto: String = "",
    val urlPhotos:List<String>
) {
    constructor() : this("", 0, "", "", emptyList())

    fun doesMatchSearchQuery(query: String): Boolean {
        return this.name.contains(
            query
        ) //Comprobamos si la cadena contiene el query
    }

}

