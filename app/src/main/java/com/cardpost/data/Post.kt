package com.cardpost.data

data class Post(
    private val text: String,
    private val date: String,
    private val like: Like,
    private val comment: Comment,
    private val reply: Repost,
    private val address: String,
    private val coordinates: Long ) {
}