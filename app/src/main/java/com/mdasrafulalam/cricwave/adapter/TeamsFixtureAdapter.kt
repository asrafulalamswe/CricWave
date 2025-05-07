package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.RecentMatchListItemBinding
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.ui.*

class TeamsFixtureAdapter : androidx.recyclerview.widget.ListAdapter<Data, TeamsFixtureAdapter.RecentViewHolder>(RecentMatchesDiffCallback()){

    class RecentViewHolder(val binding: RecentMatchListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(recentMatch: Data){
            binding.recent = recentMatch
        }
    }

    class RecentMatchesDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentMatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val recentMatch: Data = getItem(position)

        holder.bind(recentMatch)
        holder.itemView.setOnClickListener {
            val action = TeamDetailsFragmentDirections.actionTeamDetailsFragmentToMatchDetailsFragment(recentMatch,"TeamFixture")
            Navigation.findNavController(it).navigate(action)
        }
    }

}