package com.example.csgomatches.matches.ui.model

import java.util.*

data class Match(
    val status: MatchStatus,
    val beginAt: Date,
    val teams: Pair<Team, Team>,
    val league: String,
    val serie: String
)
