package com.example.postsapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postsapp.database.CommentsDatabaseDao
import com.example.postsapp.network.PostProperty

class DetailViewModelFactory(
    private val postProperty: PostProperty,
    private val application: Application,
    private val database: CommentsDatabaseDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(postProperty, application, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}