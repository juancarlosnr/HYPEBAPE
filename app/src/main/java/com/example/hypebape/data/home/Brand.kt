package com.example.hypebape.data.home

data class Brand(
    val id: Int = 0,
    val name: String = "",
    val urlPhoto: String = ""
) {
    constructor() : this(0, "", "")
}