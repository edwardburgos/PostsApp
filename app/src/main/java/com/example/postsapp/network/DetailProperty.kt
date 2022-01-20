package com.example.postsapp.network

data class DetailProperty(
    var user: String,
    val id: Int,
    var title: String,
    val body: String,
    val comments: List<CommentProperty>?
)