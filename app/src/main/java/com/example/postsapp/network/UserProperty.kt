package com.example.postsapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProperty (
    val id: Int,
    val username: String,
    val email: String

): Parcelable