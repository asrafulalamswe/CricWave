package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.SquadListItemBinding
import com.mdasrafulalam.cricwave.model.teamsquadwithteamandseasonid.Squad

class TeamSquadAdpater :ListAdapter<Squad, TeamSquadAdpater.TeamSquadViewHolder>(TeamSquadDiffCallback()){

    class TeamSquadViewHolder(val binding: SquadListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(squadInfo: Squad){
            binding.squadInfo = squadInfo
        }
    }

    class TeamSquadDiffCallback : DiffUtil.ItemCallback<Squad>() {
        override fun areItemsTheSame(oldItem: Squad, newItem: Squad): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Squad, newItem: Squad): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamSquadViewHolder {
        val binding =
            SquadListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamSquadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamSquadViewHolder, position: Int) {
        val squad: Squad = getItem(position)
        if (position%2==0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.color_date_text))
        }else if (position%2==1){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        }
        holder.bind(squad)
    }
}