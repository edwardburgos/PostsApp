package com.example.postsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CommentsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(comments: List<Comment>)

    @Update
    fun update(comment: Comment)

    @Query("DELETE FROM comment_table")
    suspend fun clear()

//    @Query("SELECT * FROM comment_table WHERE postId = :key ORDER BY id DESC ")
//    fun getPostComments(key: Int): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE postId = :key ORDER BY id DESC ")
    suspend fun getPostCommentsSuspend(key: Int): List<Comment>

    @Query("SELECT * FROM comment_table")
    fun getAllComments(): LiveData<List<Comment>>

    //@Query("INSERT INTO ")
}
