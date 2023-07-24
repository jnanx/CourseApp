package com.example.courseapp.Retrofit

import com.example.courseapp.Retrofit.Model.DotaBaseMatchDataDTO
import com.example.courseapp.Retrofit.Model.DotaBaseProMatchDataDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchService {

    @GET("matches/{id}")
    suspend fun getMatch(@Path("id") id: Long): DotaBaseMatchDataDTO

    @GET("heroes/{hero_id}/matches")
    suspend fun getProMatch(@Path("hero_id") hero_id: Int): List<DotaBaseProMatchDataDTO>

}