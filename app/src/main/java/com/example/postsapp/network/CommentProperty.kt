package com.example.postsapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentProperty (
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
): Parcelable