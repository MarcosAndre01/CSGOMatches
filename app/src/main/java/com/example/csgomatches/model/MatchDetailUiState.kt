package com.example.csgomatches.model

data class MatchDetailUiState(
    val match: Match,
    val isLoading: Boolean,
    val errorMessage: String?
)
