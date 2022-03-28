package com.example.csgomatches.matches.data

import android.util.Log
import com.example.csgomatches.matches.data.service.MatchesRemoteDataSource
import com.example.csgomatches.matches.ui.model.Match

private const val TAG = "MatchesRepository"

class MatchesRepository(private val matchesRemoteDataSource: MatchesRemoteDataSource) {

    suspend fun getMatches(): List<Match> {
        val response = matchesRemoteDataSource.getMatches(1)
        val matches = listOf<Match>()
        Log.d(TAG, "getMatches: $response")
        return matches
    }
}