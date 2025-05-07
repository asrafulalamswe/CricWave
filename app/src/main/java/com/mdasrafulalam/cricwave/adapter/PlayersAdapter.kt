package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.PlayersListItemBinding
import com.mdasrafulalam.cricwave.model.players.Data
import com.mdasrafulalam.cricwave.ui.MoreFragmentDirections
import com.mdasrafulalam.cricwave.ui.TeamDetailsFragmentDirections
import com.mdasrafulalam.cricwave.utils.MyConstants
import org.aviran.cookiebar2.CookieBar

class PlayersAdapter(private var isFromMoreFragment: Boolean) : ListAdapter<Data, PlayersAdapter.PlayersViewHolder>(PlayersDiffCallback()){
    class PlayersViewHolder(val binding: PlayersListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(playersInfo: Data){
            binding.playerInfo = playersInfo
        }
    }

    class PlayersDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val binding =
            PlayersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val playersInfo: Data = getItem(position)
        holder.bind(playersInfo)
        holder.itemView.setOnClickListener {
            if (MyConstants.verifyAvailableNetwork(holder.itemView.context)){
                if (isFromMoreFragment){
                    val action = MoreFragmentDirections.actionMoreFragmentToPlayerDetailsFragment(playersInfo)
                    holder.itemView.findNavController().navigate(action)
                }else{
                    MyConstants.CURRENT_PLAYER_ID = playersInfo.id
                    val action = TeamDetailsFragmentDirections.actionTeamDetailsFragmentToPlayerDetailsFragment(playersInfo)
                    holder.itemView.findNavController().navigate(action)
                }
            }else{
                CookieBar.build(holder.itemView.context as Activity)
                    .setTitle("No Active Internet!")
                    .setIcon(R.drawable.alert)
                    .setBackgroundColor(R.color.swipe_color_4)
                    .setCookiePosition(CookieBar.TOP)
                    .setDuration(3000) // 3 seconds
                    .show()
            }
        }
    }
}