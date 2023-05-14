package com.example.courseapp.Models

data class Ability (
    val behavior: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val icon: String? = null,
    val localized_name: String? = null,
    val scepter_grants: Boolean? = null,
    val scepter_upgrades: Boolean? = null,
    val shard_grants: Boolean? = null,
    val shard_upgrades: Boolean? = null,
    val shard_description: String? = null,
    val scepter_description: String? = null,
    val ability_special: List<AbilitySpecial>? = null,
    val mana_cost: String? = null,
    val hero_id: Int? = null,
    val cast_range: String? = null,
    val cast_point: String? = null,
    val cooldown: String? = null,
    val description: String? = null,
    val lore: String? = null,
    val dispellable: String? = null,
    val note: String? = null,
    val spell_immunity: String? = null,
    val slot: Int? = null

)