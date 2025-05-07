package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.CountrywiseFormListItemBinding
import com.mdasrafulalam.cricwave.model.standings.AllStandings

class StatsiticsStanadingAdapter : ListAdapter<AllStandings, StatsiticsStanadingAdapter.StatsiticsStanadingViewHolder>(StatsiticsStanadingDiffCallback()) {
    class StatsiticsStanadingViewHolder(val binding: CountrywiseFormListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ranking: AllStandings){
            binding.standings = ranking
        }
    }

    class StatsiticsStanadingDiffCallback : DiffUtil.ItemCallback<AllStandings>() {
        override fun areItemsTheSame(oldItem: AllStandings, newItem: AllStandings): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AllStandings, newItem: AllStandings): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsiticsStanadingViewHolder {
        val binding =
            CountrywiseFormListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatsiticsStanadingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsiticsStanadingViewHolder, position: Int) {
        val ranking: AllStandings = getItem(position)
        holder.bind(ranking)
    }
}