package com.example.postsapp.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.postsapp.database.Comment
import com.example.postsapp.database.CommentsDatabaseDao
import com.example.postsapp.network.*
import com.example.postsapp.overview.ApiStatus
import kotlinx.coroutines.*

enum class ApiStatus { LOADING, ERROR, DONE }

class DetailViewModel(
    postProperty: PostProperty,
    app: Application,
    val database: CommentsDatabaseDao
) : AndroidViewModel(app) { // AndroidViewModel(app)

//    class BusScheduleViewModel(private val scheduleDao: ScheduleDao): ViewModel() {
//
//        fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()
//
//        fun scheduleForStopName(name: String): Flow<List<Schedule>> = scheduleDao.getByStopName(name)
//    }

    // val database = database
    var commentsf = database.getPostComments(postProperty.id)


    // println(commentsf)
    private val _selectedProperty = MutableLiveData<DetailProperty>()
    val selectedProperty: LiveData<DetailProperty>
        get() = _selectedProperty

    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the status String
    val status: MutableLiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    init {
        postToDetail(postProperty)
        // _selectedProperty.value = postProperty
    }


    private suspend fun insert(comment: Comment) {
        withContext(Dispatchers.IO) {
            database.insert(comment)
            prueba()

        }
    }

    fun prueba() {
        println(5 + 25)
        println(commentsf)
    }

    fun postToDetail(postProperty: PostProperty) {
        viewModelScope.launch {
            lateinit var postComments: List<Comment>
            withContext(Dispatchers.IO) {
                postComments = database.getPostCommentsSuspend(postProperty.id)
            }
            if (postComments.size != 0) {
                _selectedProperty.value = DetailProperty(
                    postProperty.user,
                    postProperty.id,
                    postProperty.title,
                    postProperty.body,
                    postComments
                )
            } else {
                var getCommentsDeferred = Api.retrofitService.getComments(postProperty.id.toString())
                try {
                    _status.value = ApiStatus.LOADING
                    var listResult = getCommentsDeferred.await()
                    _status.value = ApiStatus.DONE
                    if (listResult.size > 0) {
                        _comments.value = listResult
                        withContext(Dispatchers.IO) {
                            database.insertAll(listResult)
                        }
                    }
                } catch (t: Throwable) {
                    _status.value = ApiStatus.ERROR
                }
                _selectedProperty.value = DetailProperty(
                    postProperty.user,
                    postProperty.id,
                    postProperty.title,
                    postProperty.body,
                    _comments.value
                )
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel() // To cancel the job when the fragment is gone
        super.onCleared()
    }


//    val displayPropertyPrice = Transformations.map(selectedProperty) {
//        app.applicationContext.getString(
//            when (it.isRental) {
//                true -> R.string.display_price_monthly_rental
//                false -> R.string.display_price
//            }, it.price
//        )
//    }

//    val displayPropertyType = Transformations.map(selectedProperty) {
//        app.applicationContext.getString(
//            R.string.display_type,
//            app.applicationContext.getString(
//                when (it.isRental) {
//                    true -> R.string.type_rent
//                    false -> R.string.type_sale
//                }
//            )
//        )
//    }
}
