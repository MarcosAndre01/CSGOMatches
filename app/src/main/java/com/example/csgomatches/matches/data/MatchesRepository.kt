package com.example.csgomatches.matches.data

import android.util.Log
import com.example.csgomatches.matches.ui.model.Match

private const val TAG = "MatchesRepository"

class MatchesRepository {

    suspend fun getMatches(): List<Match> {
        val matches = listOf<Match>()
        Log.d(TAG, "getMatches: $matches")
        return matches
    }
}