package com.example.csgomatches.data.matches.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchesLocalDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matchEntities: List<MatchEntity>)

    @Query("SELECT * FROM matchentity")
    fun pagingSource(): PagingSource<Int, MatchEntity>

    @Query("DELETE FROM matchentity")
    suspend fun clearMatches()
}