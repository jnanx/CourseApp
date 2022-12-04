package com.example.courseapp.Retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.Hero
import com.example.courseapp.Models.Player
import com.example.courseapp.Repository.HeroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DotaBaseMatchDataRepository: MatchDataRepository {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val service = retrofit.create(MatchService::class.java)


    override suspend fun getMatchData(matchId: Long): MatchData {
        val heroes =
        HeroRepository().getInstance().getHeroes()
        Log.d("repository",heroes.toString())
        return service.getMatch(matchId).run {
            MatchData(
                match_id,
                duration,
                radiant_win,
                radiant_score,
                dire_score,
                players.map
                    { dotaBasePlayerDTO ->
                        Player(
                            dotaBasePlayerDTO.personaname?: "Anonymous",
                            heroes?.firstOrNull { it.id == dotaBasePlayerDTO.hero_id },
                            dotaBasePlayerDTO.kills, dotaBasePlayerDTO.deaths, dotaBasePlayerDTO.assists
                        )
                    }
            )
        }
    }

    companion object{
        const val BASE_URL="https://api.opendota.com/api/"
    }
}