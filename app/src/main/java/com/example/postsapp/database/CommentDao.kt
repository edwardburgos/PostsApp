package com.example.postsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.postsapp.entities.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(comments: List<Comment>)

    @Update
    fun update(comment: Comment)

    @Update
    fun updateAll(comments: List<Comment>)

    @Query("DELETE FROM comment_table")
    fun clear()

    @Query("SELECT * FROM comment_table WHERE postId = :key ORDER BY id DESC ")
    fun getPostComments(key: Int): List<Comment>

    @Query("SELECT * FROM comment_table")
    fun getAllComments(): LiveData<List<Comment>>
}
