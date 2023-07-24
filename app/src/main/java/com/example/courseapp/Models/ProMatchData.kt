package com.example.courseapp.Models

data class ProMatchData(
    val match_id: Long,
    val duration: Int,
    val radiant_win: Boolean,
    val start_time: Long,
    val leagueid: Int,
    val league_name: String,
    val radiant: Boolean,
    val player_slot: Int,
    val account_id: Long,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val heroIconURL: String,

)
