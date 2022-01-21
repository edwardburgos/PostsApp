package com.example.postsapp.entities

import com.example.postsapp.entities.Comment

data class Detail(
    var user: String,
    val id: Int,
    var title: String,
    val body: String,
    val comments: List<Comment>?
)