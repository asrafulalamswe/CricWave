package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.CommentryListItemBinding
import com.mdasrafulalam.cricwave.model.fixturesbyidincludeballs.Ball

class CommentryListAdapter :ListAdapter<Ball, CommentryListAdapter.CommentryViewHolder>(CommentryDiffCallback()){
    class CommentryViewHolder(val binding: CommentryListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(commentry: Ball){
            binding.commentry = commentry
        }
    }

    class CommentryDiffCallback : DiffUtil.ItemCallback<Ball>() {
        override fun areItemsTheSame(oldItem: Ball, newItem: Ball): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Ball, newItem: Ball): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentryViewHolder {
        val binding =
            CommentryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentryViewHolder, position: Int) {
        val commentry: Ball = getItem(position)
        holder.bind(commentry)
    }
}