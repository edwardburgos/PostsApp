package com.example.postsapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.postsapp.entities.Comment
import com.example.postsapp.database.SocialDatabase
import com.example.postsapp.network.Api
import com.example.postsapp.entities.PostWithComments
import com.example.postsapp.entities.Post
import com.example.postsapp.entities.User
import com.example.postsapp.overview.ApiStatus
import kotlinx.coroutines.*

class MainRepository(
    application: Application
) {

    private val commentDao = SocialDatabase.getInstance(application).commentDao
    private val postDao = SocialDatabase.getInstance(application).postDao

    fun getComments(
        post: Post,
        viewModelScope: CoroutineScope,
        _status: MutableLiveData<ApiStatus>,
        _comments: MutableLiveData<List<Comment>>,
        _selectedPost: MutableLiveData<PostWithComments>
    ) {
        viewModelScope.launch {
            lateinit var getCommentsDeferred: Deferred<List<Comment>>
            withContext(Dispatchers.IO) {
                getCommentsDeferred =
                    Api.retrofitService.getComments(post.id.toString())
            }
            try {
                _status.value = ApiStatus.LOADING
                lateinit var listResult: List<Comment>
                withContext(Dispatchers.IO) {
                    listResult = getCommentsDeferred.await()
                }
                _status.value = ApiStatus.DONE
                if (listResult.size > 0) {
                    _comments.value = listResult
                    withContext(Dispatchers.IO) {
                        if (commentDao.getPostComments(post.id).size == 0) {
                            commentDao.insertAll(listResult)
                        } else {
                            commentDao.updateAll(listResult)
                        }
                    }
                }
            } catch (t: Throwable) {
                _status.value = ApiStatus.ERROR
            }
            val commentsRetrieved = _comments.value
            val comments: List<Comment> =
                if (commentsRetrieved != null) commentsRetrieved else listOf<Comment>()
            _selectedPost.value = PostWithComments(
                post,
                comments
            )
        }
    }

    fun getPosts(
        fromSwiper: Boolean,
        viewModelScope: CoroutineScope,
        _status: MutableLiveData<ApiStatus>,
        _properties: MutableLiveData<List<Post>>
    ) {
        viewModelScope.launch {
            lateinit var getPropertiesDeferred: Deferred<List<Post>>
            lateinit var getUsersDeferred: Deferred<List<User>>
            withContext(Dispatchers.IO) {
                getPropertiesDeferred = Api.retrofitService.getPosts()
                getUsersDeferred = Api.retrofitService.getUsers()
            }
            try {
                if (!fromSwiper) _status.value = ApiStatus.LOADING
                lateinit var listResult: List<Post>
                lateinit var users: List<User>
                withContext(Dispatchers.IO) {
                    listResult = getPropertiesDeferred.await()
                    users = getUsersDeferred.await()
                }
                _status.value = ApiStatus.DONE
                if (listResult.size > 0 && users.size > 0) {
                    for (e in listResult) {
                        val username = users.find { it.id.toString() == e.user }?.username
                        if (username != null) {
                            e.user = username
                        }
                    }
                    _properties.value = listResult
                    withContext(Dispatchers.IO) {
                        if (postDao.getAllPosts().size == 0) {
                            postDao.insertAll(listResult)
                        } else {
                            postDao.updateAll(listResult)
                        }
                    }
                }
            } catch (t: Throwable) {
                println(t.message)
                _status.value = ApiStatus.ERROR
            }
        }
    }
}