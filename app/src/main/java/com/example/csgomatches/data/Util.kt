package com.example.csgomatches.data

fun imageUrlAsThumb(defaultImageUrl: String?): String? {
    if (defaultImageUrl == null) {
        return null
    }

    val url = StringBuilder(defaultImageUrl)
    val afterLastSlash = url.lastIndexOf("/") + 1
    url.insert(afterLastSlash, "thumb_")
    return url.toString()
}