package com.mdasrafulalam.cricwave.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.ShowMoreBinding
import com.mdasrafulalam.cricwave.databinding.TeamsListItemBinding
import com.mdasrafulalam.cricwave.model.teams.Data
import com.mdasrafulalam.cricwave.ui.SeriesFragmentDirections

class TeamsAdapter(private val loadMoreData: () -> Unit) : ListAdapter<Data, RecyclerView.ViewHolder>(TeamDiffCallback()) {

    private val ITEM = 0
    private val SHOW_MORE = 1
    private var showAllData = false

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && !showAllData) SHOW_MORE else ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SHOW_MORE) {
            val binding = DataBindingUtil.inflate<ShowMoreBinding>(LayoutInflater.from(parent.context), R.layout.show_more, parent, false)
            ShowMoreViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<TeamsListItemBinding>(LayoutInflater.from(parent.context), R.layout.teams_list_item, parent, false)
            TeamsViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM) {
            val team = getItem(position)
            if (team != null) {
                (holder as TeamsViewHolder).bind(team)
                holder.itemView.setOnClickListener {
                    val action = SeriesFragmentDirections.actionSeriesFragmentToTeamDetailsFragment(team)
                    holder.itemView.findNavController().navigate(action)
                }
            }
        } else {
            (holder as ShowMoreViewHolder).bind()
        }
    }

    inner class TeamsViewHolder(private val binding: TeamsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Data) {
            binding.team = team
            binding.executePendingBindings()
        }
    }
    class TeamDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }


    inner class ShowMoreViewHolder(private val binding: ShowMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.showMoreButton.visibility =View.VISIBLE
            binding.showMoreButton.setOnClickListener {
                loadMoreData()
                binding.showMoreButton.visibility = View.VISIBLE
            }
            binding.executePendingBindings()
            /*if (!showAllData) {
                binding.showMoreButton.text = "Show More"
                binding.showMoreButton.setOnClickListener {
                    showAllData = true
                    loadMoreData()
                    notifyDataSetChanged()
                }
            } else {
                binding.showMoreButton.text = "Show Less"
                binding.showMoreButton.setOnClickListener {
                    showAllData = false
                    notifyDataSetChanged()
                }
            }
            binding.executePendingBindings()*/
        }
    }
}