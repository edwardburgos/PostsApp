package com.example.postsapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapp.entities.Comment
import com.example.postsapp.detail.CommentsAdapter
import com.example.postsapp.entities.Post
import com.example.postsapp.overview.ApiStatus
import com.example.postsapp.overview.PostsAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Post>?) {
    val adapter = recyclerView.adapter as PostsAdapter
    adapter.submitList(data)
}

@BindingAdapter("listComments")
fun bindCommentsRecyclerView(recyclerView: RecyclerView, data: List<Comment>?) {
    val adapter = recyclerView.adapter as CommentsAdapter
    adapter.submitList(data)
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
            statusImageView.getLayoutParams().height = 600;
            statusImageView.getLayoutParams().width = 600;
            statusImageView.requestLayout();
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
            statusImageView.getLayoutParams().height = 200;
            statusImageView.getLayoutParams().width = 200;
            statusImageView.requestLayout();
        }

    }
}

@BindingAdapter("ApiStatusCommentVisibility")
fun bindStatusCommentVisibility(commentsLabel: TextView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
                commentsLabel.visibility = View.GONE
        }
        ApiStatus.DONE -> {
                commentsLabel.visibility = View.VISIBLE
                commentsLabel.requestLayout()
        }
        ApiStatus.ERROR -> {
                commentsLabel.visibility = View.GONE
        }

    }
}