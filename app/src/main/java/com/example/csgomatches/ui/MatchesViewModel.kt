package com.example.csgomatches.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.csgomatches.data.matches.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(matchesRepository: MatchesRepository) : ViewModel() {
    val matches = matchesRepository.getMatches().cachedIn(viewModelScope)
}



