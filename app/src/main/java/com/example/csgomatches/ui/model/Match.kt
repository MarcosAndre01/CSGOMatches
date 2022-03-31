package com.example.csgomatches.ui.model

import java.util.*

data class Match(
    val id: Int,
    val status: MatchStatus,
    val beginAt: Date?,
    val teams: Pair<Team, Team>,
    val league: String,
    val serie: String,
    val tournamentId: Int
)
