package com.example.postsapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostProperty (
    @Json(name = "userId") var user: String,
    val id: Int,
    var title: String,
    val body: String
): Parcelable