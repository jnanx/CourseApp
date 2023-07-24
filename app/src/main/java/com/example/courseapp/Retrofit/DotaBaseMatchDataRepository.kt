package com.example.courseapp.Retrofit

import android.util.Log
import com.example.courseapp.Models.Hero
import com.example.courseapp.Models.Item
import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.Player
import com.example.courseapp.Models.ProMatchData
import com.example.courseapp.Repository.FireBaseRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DotaBaseMatchDataRepository: MatchDataRepository {

    private val client = OkHttpClient.Builder().addInterceptor(run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val repository = FireBaseRepository().getInstance()

    private val service = retrofit.create(MatchService::class.java)


    override suspend fun getMatchData(matchId: Long): MatchData {
        val heroes = repository.getHeroes()
        val items = repository.getItems()
        val abilities = repository.getAbilities()
        val talents = repository.getTalents()
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
                            heroes.firstOrNull { it.id == dotaBasePlayerDTO.hero_id },
                            dotaBasePlayerDTO.isRadiant,
                            dotaBasePlayerDTO.kills,
                            dotaBasePlayerDTO.deaths,
                            dotaBasePlayerDTO.assists,
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_0},
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_1},
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_2},
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_3},
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_4},
                            items.firstOrNull {it.id == dotaBasePlayerDTO.item_5},
                            dotaBasePlayerDTO.net_worth,
                            items.firstOrNull{it.id == dotaBasePlayerDTO.backpack_0},
                            items.firstOrNull{it.id == dotaBasePlayerDTO.backpack_1},
                            items.firstOrNull{it.id == dotaBasePlayerDTO.backpack_2},
                            dotaBasePlayerDTO.level,

                            dotaBasePlayerDTO.ability_upgrades_arr?.mapNotNull { ability ->
                                abilities.firstOrNull{it.id == ability}
                            },

                            dotaBasePlayerDTO.ability_upgrades_arr?.mapNotNull { ability ->
                                talents.firstOrNull{ it.ability_id == ability }
                            }
                        )
                    }
            )
        }
    }

    override suspend fun getProMatchData(heroId: Int): List<ProMatchData> {
        val matches = service.getProMatch(heroId)
        val hero = repository.getHeroes().firstOrNull { it.id == heroId }
        return matches.map {
            ProMatchData(
                match_id = it.match_id,
                duration = it.duration,
                radiant_win = it.radiant_win,
                start_time = it.start_time,
                leagueid = it.leagueid,
                league_name = it.league_name,
                radiant = it.radiant,
                player_slot = it.player_slot,
                account_id = it.account_id,
                kills = it.kills,
                deaths = it.deaths,
                assists = it.assists,
                heroIconURL = BASE_ICONS_URL + hero!!.icon!!
            )
        }
    }

    companion object {
        const val BASE_URL = "https://api.opendota.com/api/"
        const val BASE_ICONS_URL = "https://dotabase.dillerm.io/dota-vpk/"
    }
}