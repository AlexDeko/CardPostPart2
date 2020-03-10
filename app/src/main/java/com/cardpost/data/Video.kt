package com.cardpost.data

data class Video(
    val url: String?
) {
    public fun isHas() = when(url){
        null -> false
        else -> true
    }
}