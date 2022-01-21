package com.example.postsapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: Int,
    var username: String,
    var email: String

): Parcelable