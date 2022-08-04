package com.example.csgomatches.detail.data.tournaments.service

import com.example.csgomatches.data.API_TOKEN
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

private const val TAG = "TournamentsService"


interface TournamentsRemoteDataSource {
    @GET("tournaments/{tournamentId}/rosters")
    suspend fun getRosters(
        @Path("tournamentId") tournamentId: Int,
        @Header("Authorization") authToken: String = API_TOKEN,
    ): RostersResponse
}