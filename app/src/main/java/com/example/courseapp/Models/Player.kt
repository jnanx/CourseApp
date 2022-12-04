package com.example.courseapp.Models

data class Player(
    var name: String?,
    var hero: Hero?,
    var kills: Int?,
    val deaths: Int?,
    val assists: Int?
)
