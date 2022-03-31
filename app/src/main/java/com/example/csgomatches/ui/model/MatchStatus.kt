package com.example.csgomatches.ui.model

sealed class MatchStatus() {
    object NotStarted : MatchStatus()
    object Running : MatchStatus()
    object Finished : MatchStatus()
}