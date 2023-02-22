package com.example.courseapp.Models

data class Ability (
    val id: Int? = null,
    val name: String? = null,
    val behavior: String?= null,
    val icon: String? = null,
    val localized_name: String? = null,
    val scepter_grants: Boolean? = null,
    val scepter_upgrades: Boolean? = null,
    val shard_grants: Boolean? = null,
    val shard_upgrades: Boolean? = null
)