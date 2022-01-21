package com.example.postsapp.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class Post (
    @PrimaryKey
    var id: Int,

    @ColumnInfo
    @Json(name = "userId") var user: String,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var body: String
): Parcelable
