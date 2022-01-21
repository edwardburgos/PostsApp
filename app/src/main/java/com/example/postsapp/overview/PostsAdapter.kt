package com.example.postsapp.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapp.databinding.PostItemBinding
import com.example.postsapp.entities.Post

class PostsAdapter(val onClickListener: OnClickListener): ListAdapter<Post, PostsAdapter.PostsPropertyViewHolder>(DiffCallback) {

    class PostsPropertyViewHolder(private var binding: PostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(post: Post) {
            binding.property = post
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsPropertyViewHolder {
        return PostsPropertyViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostsPropertyViewHolder, position: Int) {
        val postProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(postProperty)
        }
        holder.bin(postProperty)
    }

    class OnClickListener(val clickListener: (post: Post) -> Unit) {
        fun onClick(post: Post) = clickListener(post)
    }
}