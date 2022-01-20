package com.example.postsapp.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * A database that stores SleepNight information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [Comment::class], version = 2, exportSchema = false)
abstract class CommentsDatabase : RoomDatabase() {

    abstract val commentDatabaseDao: CommentsDatabaseDao

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
