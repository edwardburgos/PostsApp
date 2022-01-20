package com.example.postsapp.network

import com.example.postsapp.database.Comment

data class DetailProperty(
    var user: String,
    val id: Int,
    var title: String,
    val body: String,
    val comments: List<Comment>?
)