package com.example.courseapp.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Hero(
    val id: Int?,
    val heroName: String?,
    val heroIcon: String?
)
