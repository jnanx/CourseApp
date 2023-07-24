package com.example.courseapp.Retrofit

import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.ProMatchData

interface MatchDataRepository {

    suspend fun getMatchData(matchId: Long): MatchData
    suspend fun getProMatchData(heroId: Int): List<ProMatchData>
}