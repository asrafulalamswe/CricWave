package com.mdasrafulalam.cricwave.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.cricwave.databinding.LiveMatchListItemBinding
import com.mdasrafulalam.cricwave.model.livescores.Data
import com.mdasrafulalam.cricwave.model.livescores.Run

class LiveMatchAdapter:ListAdapter<Data, LiveMatchAdapter.LiveViewHolder>(LiveMatchesDiffCallback()) {

    class LiveViewHolder(val binding: LiveMatchListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(liveMatch:Data){
            binding.liveMatchInfo = liveMatch
            var team1rr = 0.0
            var team1W = 0
            var team2W = 0
            var team2rr = 0.0
            Log.d("liveMatchinfo", "liveMatchinfo: ${liveMatch.visitorteam_id}, ${liveMatch.localteam_id}")
            val team1:MutableList<Run> = liveMatch.runs?.filter { it.team_id == liveMatch.visitorteam_id } as MutableList<Run>
            val team2:MutableList<Run> = liveMatch.runs.filter { it.team_id == liveMatch.localteam_id } as MutableList<Run>
            var team1Runs = ""
            for (i in team1){
                team1rr = if (i.overs != 0.0) i.score!! / i.overs!! else 0.0
                team1W = i.wickets!!
                team1Runs = String.format("Innings: "+i.inning.toString()+" Score: "+i.score.toString()+"/"+i.wickets+" Overs: "+i.overs+" &")
            }
            var team2Runs = ""
            for (i in team2){
                team2rr = (i.score!!/ i.overs!!)
                team2W = i.wickets!!
                team2Runs = String.format("Innings: "+i.inning.toString()+" Score: "+i.score.toString()+"/"+i.wickets+" Overs: "+i.overs+" &")
            }
            binding.liveTeam1ScoreTV.text = team1Runs
            binding.liveTeam2ScoreTV.text = team2Runs

            val team1Winning = calculateWinningPercentage(team1rr,team1W)
            val team2Winning = 100 - team1Winning

           /* val randomPercentage = (30..70).random()
            val progress2 = 100 - randomPercentage*/
            binding.winningPercentageBar.progress = team1Winning.toInt()
            binding.winningPercentageBar.secondaryProgress = team1Winning.toInt()
            binding.winning1.text = "%.2f%%".format(team1Winning)
            binding.winning2.text = "%.2f%%".format(team2Winning)
        }

        private fun calculateWinningPercentage(teamRunRate: Double, wickets: Int): Double {
            val baseRunRate = 6.0
            val maxWickets = 10

            // Clamp wickets to a valid range
            val safeWickets = wickets.coerceIn(0, maxWickets)

            // Prevent division by zero or negative factors
            if (teamRunRate <= 0.0) return 0.0

            val remainingWicketsFactor = (maxWickets - safeWickets) / 10.0
            val targetRunRate = teamRunRate * (1 - remainingWicketsFactor)

            val percentage = (targetRunRate / baseRunRate) * 100

            // Clamp to 0–100%
            return percentage.coerceIn(0.0, 100.0)
        }
    }


    class LiveMatchesDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        val binding =
            LiveMatchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LiveViewHolder, position: Int) {
        val liveMatch: Data = getItem(position)
        holder.bind(liveMatch)
    }
}