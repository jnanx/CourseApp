package com.example.courseapp.Models

import com.example.courseapp.Models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MatchData (
    val match_id: Long? = null,
    val duration: Int? = null,
    val radiant_win: Boolean? = null,
    val radiant_score: Int? = null,
    val dire_score: Int? = null,
    val players: List<Player>? = null
    )