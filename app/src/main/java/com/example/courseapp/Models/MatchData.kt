package com.example.courseapp.Models

import com.example.courseapp.Models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MatchData (
    val match_id: Long,
    val duration: Int?,
    val radiant_win: Boolean?,
    val radiant_score: Int?,
    val dire_score: Int?,
    val players: StateFlow<List<Player>>?
    )