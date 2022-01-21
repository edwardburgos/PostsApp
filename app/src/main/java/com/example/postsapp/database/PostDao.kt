package com.example.postsapp.database

import androidx.room.*
import com.example.postsapp.entities.Post
import com.example.postsapp.entities.PostWithComments

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Update
    fun update(post: Post)

    @Update
    fun updateAll(posts: List<Post>)

    @Query("DELETE FROM post_table")
    fun clear()

    @Query("SELECT * FROM post_table ORDER BY id DESC ")
    fun getAllPosts(): List<Post>

    @Transaction
    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun getAllPostsWithComments(): List<PostWithComments>
}
