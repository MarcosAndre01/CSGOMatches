package com.example.csgomatches.ui.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
class UiText(@StringRes val resId: Int, vararg val args: String) : Parcelable {
    fun asString(context: Context): String =
        context.getString(resId, *args)

}