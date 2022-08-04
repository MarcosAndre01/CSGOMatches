package com.example.csgomatches.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(val nickname: String, val name: String, val imageUrl: String?) : Parcelable
