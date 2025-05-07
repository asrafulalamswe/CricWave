package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.ScoreboardPlayersBowlingListItemBinding
import com.mdasrafulalam.cricwave.model.fixtureswithbowlingscore.Bowling

class BowlingScoreAdapter:  ListAdapter<Bowling, BowlingScoreAdapter.BowlingScoreViewHolder>(
    BowlingScoreDiffCallback()
) {

    class BowlingScoreViewHolder(val binding: ScoreboardPlayersBowlingListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(recentMatch: Bowling){
            binding.bowlingScore = recentMatch
        }
    }

    class BowlingScoreDiffCallback : DiffUtil.ItemCallback<Bowling>() {
        override fun areItemsTheSame(oldItem: Bowling, newItem: Bowling): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Bowling, newItem: Bowling): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingScoreViewHolder {
        val binding =
            ScoreboardPlayersBowlingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BowlingScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BowlingScoreViewHolder, position: Int) {
        val bowlingScore: Bowling = getItem(position)
        holder.bind(bowlingScore)
    }
}