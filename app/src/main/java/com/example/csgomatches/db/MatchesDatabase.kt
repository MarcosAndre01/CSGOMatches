package com.example.csgomatches.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.csgomatches.data.matches.local.MatchEntity
import com.example.csgomatches.data.matches.local.MatchRemoteKey
import com.example.csgomatches.data.matches.local.MatchRemoteKeyDao
import com.example.csgomatches.data.matches.local.MatchesLocalDataSource

@Database(
    entities = [MatchEntity::class, MatchRemoteKey::class],
    version = 1
)
abstract class MatchesDatabase : RoomDatabase() {
    abstract val matchesDao: MatchesLocalDataSource
    abstract val matchesRemoteKeyDao: MatchRemoteKeyDao
}