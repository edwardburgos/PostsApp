package com.example.postsapp.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.postsapp.network.*
import com.example.postsapp.overview.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class DetailViewModel(postProperty: PostProperty, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<DetailProperty>()
    val selectedProperty: LiveData<DetailProperty>
        get() = _selectedProperty

    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the status String
    val status: MutableLiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _comments = MutableLiveData<List<CommentProperty>>()
    val comments: LiveData<List<CommentProperty>>
        get() = _comments

    init {
        postToDetail(postProperty)
       // _selectedProperty.value = postProperty
    }


    fun postToDetail(postProperty: PostProperty) {
        coroutineScope.launch {
            var getCommentsDeferred = Api.retrofitService.getComments(postProperty.id.toString())

            try {
                _status.value = ApiStatus.LOADING
                var listResult = getCommentsDeferred.await()
                _status.value = ApiStatus.DONE
                if (listResult.size > 0) {
                    _comments.value = listResult
                }
            } catch (t: Throwable) {
                _status.value = ApiStatus.ERROR
            }
            _selectedProperty.value = DetailProperty(
                postProperty.user,
            postProperty.id,
            postProperty.title,
           postProperty.body,
            _comments.value)
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
