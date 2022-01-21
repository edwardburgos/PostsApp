package com.example.postsapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.postsapp.entities.Comment
import com.example.postsapp.database.CommentsDatabase
import com.example.postsapp.network.Api
import com.example.postsapp.entities.Detail
import com.example.postsapp.entities.Post
import com.example.postsapp.entities.User
import com.example.postsapp.overview.ApiStatus
import kotlinx.coroutines.*

class MainRepository(
    application: Application
) {

    private val commentsDatabaseDao = CommentsDatabase.getInstance(application).commentsDatabaseDao

    fun getComments(
        post: Post,
        viewModelScope: CoroutineScope,
        _status: MutableLiveData<ApiStatus>,
        _comments: MutableLiveData<List<Comment>>,
        _selectedPost: MutableLiveData<Detail>
    ) {
        // api.retrofitService.getComments(postId)
        viewModelScope.launch {
            lateinit var postComments: List<Comment>
            withContext(Dispatchers.IO) {
                postComments = commentsDatabaseDao.getPostCommentsSuspend(post.id)
            }
            if (postComments.size != 0) {
                _selectedPost.value = Detail(
                    post.user,
                    post.id,
                    post.title,
                    post.body,
                    postComments
                )
            } else {
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
                            commentsDatabaseDao.insertAll(listResult)
                        }
                    }
                } catch (t: Throwable) {
                    _status.value = ApiStatus.ERROR
                }
                _selectedPost.value = Detail(
                    post.user,
                    post.id,
                    post.title,
                    post.body,
                    _comments.value
                )
            }
        }
    }

    fun getPosts(
        viewModelScope: CoroutineScope,
        _status: MutableLiveData<ApiStatus>,
        _properties: MutableLiveData<List<Post>>
    ) {
        viewModelScope.launch {
            // This is a coroutine scope
            var getPropertiesDeferred = Api.retrofitService.getPosts()
            var getUsersDeferred = Api.retrofitService.getUsers()
            try {
                _status.value = ApiStatus.LOADING
                var listResult = getPropertiesDeferred.await()
                var users = getUsersDeferred.await()
                _status.value = ApiStatus.DONE
                if (listResult.size > 0 && users.size > 0) {
                    for (e in listResult) {
                        var username = users.find { it.id.toString() == e.user }?.username
                        if (username != null) {
                            e.user = username
                        }
                    }
                    _properties.value = listResult
                }
            } catch (t: Throwable) {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}