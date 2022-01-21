package com.example.postsapp.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithComments(
    @Embedded var post: Post,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    var comments: List<Comment>
)