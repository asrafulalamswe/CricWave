package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.RankingListItemBinding
import com.mdasrafulalam.cricwave.model.teamrankings.Team

class RankingAdapter : ListAdapter<Team, RankingAdapter.RankingViewHolder>(RankingDiffCallback()){

    class RankingViewHolder(val binding: RankingListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ranking: Team){
            binding.teamRanking = ranking
        }
    }
    class RankingDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding =
            RankingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val ranking: Team = getItem(position)
        if (position%2==0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.color_date_text))
        }else if (position%2==1){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        holder.bind(ranking)
    }

}