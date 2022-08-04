package com.example.csgomatches.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MatchStatus : Parcelable {
    @Parcelize
    object NotStarted : MatchStatus(), Parcelable
    @Parcelize
    object Running : MatchStatus(), Parcelable
    @Parcelize
    object Finished : MatchStatus(), Parcelable
}