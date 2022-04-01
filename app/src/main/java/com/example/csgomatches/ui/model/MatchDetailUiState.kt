package com.example.csgomatches.ui.model

data class MatchDetailUiState(
    val match: Match,
    val isLoading: Boolean,
    val errorMessage: String?
)
