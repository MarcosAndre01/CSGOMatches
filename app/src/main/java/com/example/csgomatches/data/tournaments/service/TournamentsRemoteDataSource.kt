package com.example.csgomatches.data.tournaments.service

import android.util.Log
import com.example.csgomatches.data.API_TOKEN
import com.example.csgomatches.data.retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

private const val TAG = "TournamentsService"

object TournamentsRemoteDataSource {
    suspend fun getRosters(tournamentId: Int): RostersResponse {
        Log.d(TAG, "getRosters: tournamentId: $tournamentId")
        return tournamentsService.getRosters(tournamentId)
    }
}

private val tournamentsService: TournamentsService by lazy {
    retrofit.create(TournamentsService::class.java)
}

private interface TournamentsService {
    @GET("tournaments/{tournamentId}/rosters")
    suspend fun getRosters(
        @Path("tournamentId") tournamentId: Int,
        @Header("Authorization") authToken: String = API_TOKEN,
    ): RostersResponse
}