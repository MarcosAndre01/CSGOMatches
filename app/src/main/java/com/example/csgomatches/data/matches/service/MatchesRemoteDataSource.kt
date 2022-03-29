package com.example.csgomatches.matches.data.service

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

object MatchesRemoteDataSource {
    suspend fun getMatches(page: Int): List<MatchResponse> = matchesService.getMatches(page)
}

private val matchesService: MatchesService by lazy {
    retrofit.create(MatchesService::class.java)
}

private interface MatchesService {
    @GET("csgo/matches")
    suspend fun getMatches(
        @Query("page[number]") page: Int,
        @Header("Authorization") authToken: String = API_TOKEN,
    ): List<MatchResponse>
}