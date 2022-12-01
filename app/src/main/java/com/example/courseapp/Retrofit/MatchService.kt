package com.example.courseapp.Retrofit

import com.example.courseapp.Retrofit.Model.DotaBaseMatchDataDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchService {

    @GET("matches/{id}")
    suspend fun getMatch(@Path("id") id: Long): DotaBaseMatchDataDTO

}