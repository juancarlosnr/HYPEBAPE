package com.example.hypebape.data.home

data class Advertising(
    var id:Int = 0,
    var urlAdvertising: String,
    var priority:Int = 0
){
    constructor() : this(0, "", 0)
}