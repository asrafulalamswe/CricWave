package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.RecentMatchListItemBinding
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.ui.TournamentFixturesFragmentDirections

class TournamentFixtureAdapter(val isFromHome:Boolean, val isFromSeries:Boolean, val isFromBookMark:Boolean): ListAdapter<Data, TournamentFixtureAdapter.TournamentViewHolder>(RecentMatchesDiffCallback()) {

    class TournamentViewHolder(val binding: RecentMatchListItemBinding): RecyclerView.ViewHolder(binding.root){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val binding =
            RecentMatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TournamentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val recentMatch: Data = getItem(position)
        holder.bind(recentMatch)
        holder.itemView.setOnClickListener {
            if (isFromSeries){
                val action = TournamentFixturesFragmentDirections.actionTournamentFixturesFragmentToMatchDetailsFragment(recentMatch,"TournamentFixtureSeries")
                holder.itemView.findNavController().navigate(action)
            }else if (isFromHome){
                val action = TournamentFixturesFragmentDirections.actionTournamentFixturesFragmentToMatchDetailsFragment(recentMatch,"TournamentFixtureHome")
                holder.itemView.findNavController().navigate(action)
            }else if (isFromBookMark){
                val action = TournamentFixturesFragmentDirections.actionTournamentFixturesFragmentToMatchDetailsFragment(recentMatch,"TournamentFixtureBookMark")
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

}