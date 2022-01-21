package com.example.postsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postsapp.entities.Comment
import com.example.postsapp.entities.Post

@Database(entities = [Comment::class, Post::class], version = 3, exportSchema = false)
abstract class SocialDatabase : RoomDatabase() {

    abstract val commentDao: CommentDao
    abstract val postDao: PostDao

    companion object {

        @Volatile
        private var INSTANCE: SocialDatabase? = null

        fun getInstance(context: Context): SocialDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SocialDatabase::class.java,
                        "social_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
