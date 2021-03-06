package com.example.postsapp.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.postsapp.entities.Comment
import com.example.postsapp.entities.Post
import com.example.postsapp.entities.PostWithComments
import com.example.postsapp.overview.ApiStatus
import com.example.postsapp.repository.MainRepository

class DetailViewModel(
    post: Post,
    app: Application
) : AndroidViewModel(app) {

    private val mainRepository = MainRepository(app)

    private val _selectedPost = MutableLiveData<PostWithComments>()
    val selectedProperty: LiveData<PostWithComments>
        get() = _selectedPost

    private val _status = MutableLiveData<ApiStatus>()
    val status: MutableLiveData<ApiStatus>
        get() = _status

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    init {
        postToDetail(post)
    }

    fun postToDetail(post: Post) {
        mainRepository.getComments(post, viewModelScope, _status, _comments, _selectedPost)
    }
}
