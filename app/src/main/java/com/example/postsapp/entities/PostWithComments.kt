package com.example.postsapp.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.postsapp.entities.Comment

//data class Detail(
//    var user: String,
//    var id: Int,
//    var title: String,
//    var body: String,
//    var comments: List<Comment>?
//)

data class PostWithComments(
    @Embedded var post: Post,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    var comments: List<Comment>
)