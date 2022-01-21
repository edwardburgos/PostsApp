package com.example.postsapp.overview

import android.app.Application
import androidx.lifecycle.*
import com.example.postsapp.entities.Comment
import com.example.postsapp.entities.Post
import com.example.postsapp.entities.User
import com.example.postsapp.network.*
import com.example.postsapp.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val mainRepository = MainRepository(app)

    private val _status = MutableLiveData<ApiStatus>()
    val status: MutableLiveData<ApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<Post>>()
    val properties: LiveData<List<Post>>
        get() = _properties

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    private val _navigateToSelectedProperty = MutableLiveData<Post>()
    val navigateToSelectedPost: LiveData<Post>
        get() = _navigateToSelectedProperty

    init {
        getPosts(false)
    }

    fun getPosts(fromSwiper: Boolean) {
        mainRepository.getPosts(fromSwiper, viewModelScope, _status, _properties)
    }

    fun displayPropertyDetails(post: Post) {
        _navigateToSelectedProperty.value = post
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
