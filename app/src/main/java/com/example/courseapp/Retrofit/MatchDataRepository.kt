package com.example.courseapp.Retrofit

import com.example.courseapp.Models.MatchData

interface MatchDataRepository {

    suspend fun getMatchData(matchId: Long): MatchData
}