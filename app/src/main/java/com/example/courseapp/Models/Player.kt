package com.example.courseapp.Models

data class Player(
    val name: String?,
    val hero: Hero?,
    val isRadiant: Boolean? = null,
    val kills: Int?,
    val deaths: Int?,
    val assists: Int?,
    val item_0: Item?,
    val item_1: Item?,
    val item_2: Item?,
    val item_3: Item?,
    val item_4: Item?,
    val item_5: Item?,
    val net_worth: Int?,
    val backpack_0: Item?,
    val backpack_1: Item?,
    val backpack_2: Item?,
    val level: Int?,
    val ability_upgrades: List<Ability>?,
    val talent_upgrades: List<Talent>?
)
