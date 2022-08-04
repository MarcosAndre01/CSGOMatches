package com.example.csgomatches.ui

import androidx.lifecycle.*
import com.example.csgomatches.data.tournaments.TournamentsRepository
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(private val tournamentsRepository: TournamentsRepository) : ViewModel() {
    private val _uiState: MutableLiveData<MatchDetailUiState> = MutableLiveData()
    val uiState: LiveData<MatchDetailUiState>
        get() = _uiState

    fun selectedMatch(match: Match) {
        viewModelScope.launch {
            _uiState.value = MatchDetailUiState(match, isLoading = true, errorMessage = null)

            try {
                _uiState.value = MatchDetailUiState(
                    fetchPlayersForMatch(match),
                    isLoading = false,
                    errorMessage = null
                )
            } catch (exception: IOException) {
                _uiState.value =
                    MatchDetailUiState(match, isLoading = false, exception.localizedMessage)
            } catch (exception: HttpException) {
                _uiState.value =
                    MatchDetailUiState(match, isLoading = false, exception.localizedMessage)
            }
        }
    }

    private suspend fun fetchPlayersForMatch(match: Match): Match {
        val players = tournamentsRepository.getRostersForMatch(match)
        return match.copy(
            firstTeam = match.firstTeam.copy(
                players = players?.first
            ),
            secondTeam = match.secondTeam.copy(
                players = players?.second
            )
        )
    }

}