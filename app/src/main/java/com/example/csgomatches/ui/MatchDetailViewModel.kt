package com.example.csgomatches.ui

import androidx.lifecycle.*
import com.example.csgomatches.data.tournaments.TournamentsRepository
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchDetailUiState
import kotlinx.coroutines.launch

class MatchDetailViewModel(val tournamentsRepository: TournamentsRepository) : ViewModel() {
    private val _uiState: MutableLiveData<MatchDetailUiState> = MutableLiveData()
    val uiState: LiveData<MatchDetailUiState>
        get() = _uiState

    fun selectedMatch(match: Match) {
        viewModelScope.launch {
            val players = tournamentsRepository.getRostersForMatch(match)
            val newMatch = match.copy(
                teams = match.teams.copy(
                    first = match.teams.first.copy(
                        players = players?.first
                    ),
                    second = match.teams.second.copy(
                        players = players?.second
                    )
                )
            )

            _uiState.value = MatchDetailUiState(newMatch)
        }
    }

    class Factory(private val tournamentsRepository: TournamentsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchDetailViewModel(tournamentsRepository) as T
        }
    }
}