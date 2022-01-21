package com.example.postsapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val id: Int,
    val username: String,
    val email: String

): Parcelable