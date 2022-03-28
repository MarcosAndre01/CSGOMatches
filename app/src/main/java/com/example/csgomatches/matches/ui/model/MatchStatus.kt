package com.example.csgomatches.matches.ui.model

sealed class MatchStatus() {
    object NotStarted : MatchStatus()
    object Running : MatchStatus()
    object Finished : MatchStatus()
}