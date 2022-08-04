package com.example.csgomatches.data.matches.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MatchRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val matchRemoteId: Int,
    val nextPage: Int?
)