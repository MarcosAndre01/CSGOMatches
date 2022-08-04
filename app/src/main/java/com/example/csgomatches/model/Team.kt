package com.example.csgomatches.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Team(val id: Int, val name: String, val imageUrl: String?, val players: List<Player>?) :
    Parcelable, Serializable