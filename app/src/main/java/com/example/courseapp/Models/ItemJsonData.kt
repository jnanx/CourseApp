package com.example.courseapp.Models

import com.google.firebase.database.PropertyName


data class ItemJsonData (

    @PropertyName("ItemSellable")
    val ItemSellable: String? = null,
    @PropertyName("ItemIsNeutralDrop")
    val ItemIsNeutralDrop: String? = null


)
