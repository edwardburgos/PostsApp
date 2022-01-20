package com.example.postsapp.network

import com.example.postsapp.database.Comment
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface ApiService {
    @GET("posts")
    fun getPosts():
            Deferred<List<PostProperty>>

    @GET("users")
    fun getUsers():
            Deferred<List<UserProperty>>

    @GET("comments")
    fun getComments(@Query("postId") type: String):
            Deferred<List<Comment>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 * This object is going to be exposed to all the app
 */
object Api {

    /* retrofitService returns a Retrofit object that implements MarsApiService */
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}