package com.example.csgomatches.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.csgomatches.data.matches.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MatchesViewModel"

@HiltViewModel
class MatchesViewModel @Inject constructor(matchesRepository: MatchesRepository) : ViewModel() {
    init {
        Log.d(TAG, "init: created")
    }


    val matches = matchesRepository.getMatches().cachedIn(viewModelScope)
}



