package com.example.postsapp.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post (
    @Json(name = "userId") var user: String,
    val id: Int,
    var title: String,
    val body: String
): Parcelable