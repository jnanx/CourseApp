package com.example.courseapp.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    val id: Int? = null,
    val name: String? = null,
    val localized_name: String? = null,
    val quality: String? = null,
    val icon: String? = null,
    val cost: Int? = null,
    val cooldown: String? = null,
    val cast_range: String? = null,
    val mana_cost: String? = null,
    val secret_shop: Boolean? = null

//    val ability_special: List<>? = null,
//    val aliases: String? = null,
//    val cost: Int? = null,
//    val icon: String? = null,
//    val id: Int? = null,
//    val json_data: List<>? = null,
//    val localized_name: String? = null,
//    val lore: String? = null,
//    val name: String? = null,
//    val quality: String? = null,
//    val secret_shop: Boolean? = null,
//    val shop_tags: String? = null
    ){

}