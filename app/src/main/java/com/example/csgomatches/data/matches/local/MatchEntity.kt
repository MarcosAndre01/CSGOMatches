package com.example.csgomatches.data.matches.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: Int,
    val status: String,
    val beginAt: String?,
    val firstTeamId: Int,
    val firstTeamName: String,
    val firstTeamImageUrl: String?,
    val secondTeamId: Int,
    val secondTeamName: String,
    val secondTeamImageUrl: String?,
    val league: String,
    val serie: String?,
    val tournamentId: Int
)

