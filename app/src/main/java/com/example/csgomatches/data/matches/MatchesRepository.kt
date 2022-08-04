package com.example.csgomatches.data.matches

import android.util.Log
import androidx.paging.*
import com.example.csgomatches.data.PAGE_SIZE
import com.example.csgomatches.data.matches.paging.MatchesRemoteMediator
import com.example.csgomatches.data.matches.remote.MatchesRemoteDataSource
import com.example.csgomatches.db.MatchesDatabase
import com.example.csgomatches.ui.model.Match
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "MatchesRepository"

class MatchesRepository @Inject constructor(
    private val matchesDatabase: MatchesDatabase,
    private val matchesRemoteDataSource: MatchesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMatches(): Flow<PagingData<Match>> = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MatchesRemoteMediator(matchesDatabase, matchesRemoteDataSource)
        ) {
            matchesDatabase.matchesDao.pagingSource()
        }.flow
            .map { pagingData ->
                pagingData.map { matchEntity ->
                    Log.d(TAG, "getMatches: $matchEntity")
                    matchEntity.toMatch()
                }
            }.flowOn(dispatcher)

}