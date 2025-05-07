package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.SeriesCompetitionnsListItemBinding
import com.mdasrafulalam.cricwave.model.stages.Series
import com.mdasrafulalam.cricwave.ui.BookMarkFragmentDirections
import com.mdasrafulalam.cricwave.ui.HomeFragmentDirections
import com.mdasrafulalam.cricwave.ui.SeriesFragmentDirections

class CompetitionsAdapter(private val isFromHome:Boolean, private val isFromSeries: Boolean, val addBookmarkCallback:  (Series) -> Unit): ListAdapter<Series, CompetitionsAdapter.CompetitionViewHolder>(
    CompetitionDiffCallback()
) {
    class CompetitionViewHolder(val binding: SeriesCompetitionnsListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(series: Series){
            binding.series = series
        }
    }

    class CompetitionDiffCallback : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val binding = SeriesCompetitionnsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompetitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        val series: Series = getItem(position)
        holder.bind(series)
        holder.binding.favouriteIV.setOnClickListener {
            series.isBookMarked = !series.isBookMarked
            addBookmarkCallback(series)
            holder.bind(series)
        }
        holder.itemView.setOnClickListener {
            if (isFromSeries){
                val action = SeriesFragmentDirections.actionSeriesFragmentToTournamentFixturesFragment(series.id,"Series")
                holder.itemView.findNavController().navigate(action)
            }else if (isFromHome){
                val action = HomeFragmentDirections.actionHomeFragmentToTournamentFixturesFragment(series.id,"Home")
                holder.itemView.findNavController().navigate(action)
            }
            else{
                val action = BookMarkFragmentDirections.actionBookMarkFragmentToTournamentFixturesFragment(series.id,"BookMark")
                holder.itemView.findNavController().navigate(action)
            }
        }
    }
}