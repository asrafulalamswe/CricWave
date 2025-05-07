package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.ScoreboardPlayersRunsListItemBinding
import com.mdasrafulalam.cricwave.model.fixtureswithbattingscore.Batting

class BattingScoreAdapter :
    ListAdapter<Batting, BattingScoreAdapter.BattingScoreViewHolder>(BattingScoreDiffCallback()) {

    class BattingScoreViewHolder(val binding: ScoreboardPlayersRunsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentMatch: Batting) {
            binding.score = recentMatch
        }
    }

    class BattingScoreDiffCallback : DiffUtil.ItemCallback<Batting>() {
        override fun areItemsTheSame(oldItem: Batting, newItem: Batting): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Batting, newItem: Batting): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingScoreViewHolder {
        val binding = ScoreboardPlayersRunsListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BattingScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattingScoreViewHolder, position: Int) {
        val battingScore: Batting = getItem(position)
        holder.bind(battingScore)
    }
}