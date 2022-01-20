package com.example.postsapp.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapp.databinding.CommentItemBinding
import com.example.postsapp.databinding.PostItemBinding
import com.example.postsapp.network.CommentProperty
import com.example.postsapp.network.PostProperty

class CommentsAdapter(): ListAdapter<CommentProperty, CommentsAdapter.CommentsPropertyViewHolder>(DiffCallback) {
    class CommentsPropertyViewHolder(private var binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(commentProperty: CommentProperty) {
            binding.property = commentProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CommentProperty>() {
        override fun areItemsTheSame(oldItem: CommentProperty, newItem: CommentProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CommentProperty, newItem: CommentProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsPropertyViewHolder {
        return CommentsPropertyViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CommentsPropertyViewHolder, position: Int) {
        val commentProperty = getItem(position)
        holder.bin(commentProperty)
    }
}