package com.example.postsapp.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey
    var id: Int,

    @ColumnInfo
    var postId: Int,

    @ColumnInfo
    var email: String,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var body: String,
) : Parcelable