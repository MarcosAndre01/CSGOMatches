package com.example.csgomatches.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.csgomatches.data.matches.MatchesRepository

class MatchesViewModel(private val matchesRepository: MatchesRepository) : ViewModel() {
    val matches = matchesRepository.getMatches().cachedIn(viewModelScope)

    class Factory(private val matchesRepository: MatchesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchesViewModel(matchesRepository) as T
        }
    }
}



