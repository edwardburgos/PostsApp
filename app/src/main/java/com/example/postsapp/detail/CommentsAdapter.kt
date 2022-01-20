package com.example.postsapp.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapp.database.Comment
import com.example.postsapp.databinding.CommentItemBinding
import com.example.postsapp.databinding.PostItemBinding
import com.example.postsapp.network.CommentProperty
import com.example.postsapp.network.PostProperty

class CommentsAdapter(): ListAdapter<Comment, CommentsAdapter.CommentViewHolder>(DiffCallback) {
    class CommentViewHolder(private var binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(comment: Comment) {
            binding.property = comment
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        return CommentViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bin(comment)
    }
}