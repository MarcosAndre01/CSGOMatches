package com.example.csgomatches.data.matches.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.csgomatches.data.matches.local.MatchEntity
import com.example.csgomatches.data.matches.local.MatchRemoteKey
import com.example.csgomatches.data.matches.remote.MatchesRemoteDataSource
import com.example.csgomatches.data.matches.toEntity
import com.example.csgomatches.db.MatchesDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "MatchesRemoteMediator"

@OptIn(ExperimentalPagingApi::class)
class MatchesRemoteMediator(
    private val database: MatchesDatabase,
    private val remoteApi: MatchesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Int, MatchEntity>() {
    val matchesDao = database.matchesDao
    val remoteKeysDao = database.matchesRemoteKeyDao

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MatchEntity>
    ): MediatorResult = withContext(dispatcher) {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return@withContext MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return@withContext MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = remoteApi.getMatches(currentPage)

            val endOfPaginationReached = response.isEmpty()
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    matchesDao.clearMatches()
                    remoteKeysDao.deleteAllRemoteKeys()
                }

                val matchEntities = response.filter { it.opponents.size == 2 }.map { it.toEntity() }
                matchesDao.insertMatches(matchEntities)

                val remotePages = response.map { matchResponse ->
                    MatchRemoteKey(
                        matchRemoteId = matchResponse.id,
                        nextPage = nextPage
                    )
                }
                remoteKeysDao.addAllRemoteKeys(remoteKeys = remotePages)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MatchEntity>
    ): MatchRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { matchEntity ->
                remoteKeysDao.getRemoteKey(matchEntity.remoteId)
            }
    }
}