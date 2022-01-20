package com.example.postsapp.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postsapp.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent status
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the status String
    val status: MutableLiveData<ApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<PostProperty>>()
    val properties: LiveData<List<PostProperty>>
        get() = _properties

    private val _users = MutableLiveData<List<UserProperty>>()
    val users: LiveData<List<UserProperty>>
        get() = _users

    private val _comments = MutableLiveData<List<CommentProperty>>()
    val comments: LiveData<List<CommentProperty>>
        get() = _comments

    private val _navigateToSelectedProperty = MutableLiveData<PostProperty>()
    val navigateToSelectedProperty: LiveData<PostProperty>
        get() = _navigateToSelectedProperty

    // To use deferred instead of callback
    // With this we can easily update the value of our MutableLiveData when we get a result
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getPosts()
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or
     * the successful number of Mars properties retrieved.
     * This method calls Retrofit service to handle the response
     */
//    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
    private fun getPosts() {
        // CON CALLBACK EN EL MÉTODO ENQUEUE
//        // enqueue() start the network request on a background thread. Its parameter contains methods
//        // that can be invoked when the request is completed
//        MarsApi.retrofitService.getProperties().enqueue(object: Callback<List<MarsProperty>> {
//            override fun onResponse(
//                call: Call<List<MarsProperty>>,
//                response: Response<List<MarsProperty>>
//            ) {
//                _status.value = "${status.body()?.size}"
//
//            }
//
//            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                _status.value = "Failure: ${t.message}"
//            }
//        })

        // CON CO-RUTINAS
        coroutineScope.launch {
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
                    _users.value = users
                    _properties.value = listResult
                }
            } catch (t: Throwable) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel() // To cancel the job when the fragment is gone
        super.onCleared()
    }

    fun displayPropertyDetails(postProperty: PostProperty) {
        coroutineScope.launch {
            var getCommentsDeferred = Api.retrofitService.getComments(postProperty.id.toString())
            try {
                var listResult = getCommentsDeferred.await()
                if (listResult.size > 0) {
                    for (e in listResult) {
                        var username = _users.value?.find { it.email == e.email }?.username
                        if (username != null) {
                            e.username = username
                        }
                    }
                    _comments.value = listResult
                }
            } catch (t: Throwable) {
                _status.value = ApiStatus.ERROR
            }
        _navigateToSelectedProperty.value = postProperty
            //DetailProperty(
//                postProperty.user,
//            postProperty.id,
//            postProperty.title,
//            postProperty.body,
//            _comments.value)
        }
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    // To update data when filter argument change
//    fun updateFilter(filt) {
//        getMarsRealEstateProperties(filter)
//    }
}
