package com.example.postsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postsapp.entities.Comment

@Database(entities = [Comment::class], version = 2, exportSchema = false)
abstract class CommentsDatabase : RoomDatabase() {

    abstract val commentsDatabaseDao: CommentsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CommentsDatabase? = null

        fun getInstance(context: Context): CommentsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommentsDatabase::class.java,
                        "comments_database"
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
