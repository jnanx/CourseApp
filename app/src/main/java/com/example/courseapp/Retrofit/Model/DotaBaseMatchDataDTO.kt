package com.example.courseapp.Retrofit.Model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DotaBaseMatchDataDTO(
    val match_id: Long,
    val duration: Int,
    val radiant_win: Boolean,
    val radiant_score: Int,
    val dire_score: Int,
    val players: List<DotaBasePlayerDTO>
)