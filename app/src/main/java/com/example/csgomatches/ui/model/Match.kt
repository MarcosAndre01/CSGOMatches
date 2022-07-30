package com.example.csgomatches.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Match(
    val id: Int,
    val status: MatchStatus,
    val beginAt: UiText?,
    val teams: Pair<Team, Team>,
    val league: String,
    val serie: String?,
    val tournamentId: Int
) : Parcelable
