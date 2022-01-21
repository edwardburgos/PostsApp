package com.example.postsapp.entities

import com.example.postsapp.entities.Comment

data class Detail(
    var user: String,
    var id: Int,
    var title: String,
    var body: String,
    var comments: List<Comment>?
)