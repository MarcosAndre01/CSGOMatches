package com.example.csgomatches.data.matches.service

import com.example.csgomatches.data.API_TOKEN
import com.example.csgomatches.data.retrofit
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
        @Query("sort") sort: String = "-status,begin_at",
        @Query("filter[status]") statusFilter: String = "running,not_started",
        @Query("filter[unscheduled]") showUnscheduled: Boolean = false,
        // @Query("filter[detailed_stats]") showOnlyWithDetailedStats: Boolean = true,
        @Header("Authorization") authToken: String = API_TOKEN,
    ): List<MatchResponse>
}