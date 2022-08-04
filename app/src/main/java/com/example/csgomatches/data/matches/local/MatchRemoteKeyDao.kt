package com.example.csgomatches.data.matches.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: MatchRemoteKey)

    @Query("SELECT * FROM matchremotekey WHERE matchRemoteId = :matchRemoteId")
    suspend fun getRemoteKey(matchRemoteId: Int): MatchRemoteKey

    @Query("DELETE FROM matchremotekey WHERE matchRemoteId = :matchRemoteId")
    suspend fun deleteKey(matchRemoteId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MatchRemoteKey>)

    @Query("DELETE FROM matchremotekey")
    suspend fun deleteAllRemoteKeys()
}