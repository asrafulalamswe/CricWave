package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.RecentMatchListItemBinding
import com.mdasrafulalam.cricwave.model.fixturesfilteringdateincludingruns.Data
import com.mdasrafulalam.cricwave.ui.MatchesFragmentDirections
import com.mdasrafulalam.cricwave.ui.RecentMatchesFragmentDirections
import com.mdasrafulalam.cricwave.ui.T20LeaguesFragmentDirections
import com.mdasrafulalam.cricwave.utils.MyConstants

class RecentMatchAdapter(private val isFromMatchViewPager: Boolean, private val isFromPopularT20ViewPager: Boolean) : androidx.recyclerview.widget.ListAdapter<Data, RecentMatchAdapter.RecentViewHolder>(RecentMatchesDiffCallback()){
    class RecentViewHolder(val binding: RecentMatchListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(recentMatch:Data){
            binding.recent = recentMatch
            if (MyConstants.ALL_RUNS.value?.isNotEmpty() == true){
                val runs = MyConstants.ALL_RUNS.value?.filter { it.fixture_id == recentMatch.id }
                if (runs != null) {
                    if (runs.isNotEmpty()){
                       if (runs.size>1){
                           binding.recentTeam2ScoreTV.text = String.format(""+ (runs[0].score ?: 0) + "/"+ (runs[0].wickets
                               ?: 0) +"("+ (runs[0].overs ?: 0) +" overs)")
                           binding.recentTeam1ScoreTV.text = String.format(""+ (runs[1].score ?: 0) + "/"+ (runs[1].wickets
                               ?: 0) +"("+ (runs[1].overs ?: 0) +" overs)")
                       }
                    }
                }
            }

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
            if (isFromMatchViewPager) {
                val action = MatchesFragmentDirections.actionMatchesFragmentToMatchDetailsFragment(recentMatch, "MatchViewPager")
                Navigation.findNavController(it).navigate(action)
            }else if (isFromPopularT20ViewPager){
                val action = T20LeaguesFragmentDirections.actionT20LeaguesFragmentToMatchDetailsFragment(recentMatch, "PopularT20ViewPager")
                Navigation.findNavController(it).navigate(action)
            }
            else {
                val action = RecentMatchesFragmentDirections.actionRecentMatchesFragmentToMatchDetailsFragment(recentMatch,"Drawer")
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

}