package com.example.csgomatches.matches.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val API_TOKEN = "3Que10vGy2HHrOgp5ZnMFmYL1xH6881Dk998s2E-QsziclcKGTs"
private const val BASE_URL = "https://api.pandascore.co/"

object MatchesRemoteDataSource {
    suspend fun getMatches(page: Int): List<MatchResponse> = matchesService.getMatches(page)
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
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