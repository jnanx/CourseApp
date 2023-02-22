package com.example.courseapp.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Hero(
    var id: Int? = null,
    var name: String? = null,
    var full_name: String? = null,
    var media_name: String? = null,
    var localized_name: String? = null,
    var aliases: String? = null,
    var roles: String? = null,
    var role_levels: String? = null,
    var hype: String? = null,
    var bio: String? = null,
    var image: String? = null,
    var icon: String? = null,
    var portrait: String? = null,
    var color: String? = null,
    var legs: Int? = null,
    var team: String? = null,
    var base_health_regen: Float? = null,
    var base_mana_regen: Float? = null,
    var base_movement: Int? = null,
    var base_attack_speed: Int? = null,
    var turn_rate: Float? = null,
    var base_armor: Int? = null,
    var attack_range: Int? = null,
    var attack_projectile_speed: Int? = null,
    var attack_damage_min: Int? = null,
    var attack_damage_max: Int? = null,
    var attack_rate: Float? = null,
    var attack_point: Float? = null,
    var attr_primary: String? = null,
    var attr_strength_base: Int? = null,
    var attr_strength_gain: Float? = null,
    var attr_intelligence_base: Int? = null,
    var attr_intelligence_gain: Float? = null,
    var attr_agility_base: Int? = null,
    var attr_agility_gain: Float? = null,
    var vision_day: Int? = null,
    var vision_night: Int? = null,
    var magic_resistance: Int? = null,
    var is_melee: Boolean? = null,
)
