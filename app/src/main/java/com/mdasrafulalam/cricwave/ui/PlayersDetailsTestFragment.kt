package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.FragmentPlayersDetailsTestBinding
import com.mdasrafulalam.cricwave.model.playerinfowithcareerbyid.Batting
import com.mdasrafulalam.cricwave.model.playerinfowithcareerbyid.Bowling
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch
import kotlin.math.round

class PlayersDetailsTestFragment : Fragment() {
    private lateinit var binding: FragmentPlayersDetailsTestBinding
    private val viewModel: CricketViewModel by activityViewModels()
    private var isBattingShown = false
    private var isBowlingShown = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlayersDetailsTestBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val playersCareer = viewModel.getPlayerByIdIncludingCareer(MyConstants.CURRENT_PLAYER_ID)
            val batting = playersCareer.filter { it.type == "TEST" }.map { it.batting }
            val bowling = playersCareer.filter { it.type == "TEST" }.map { it.bowling }
            //battting
            var totalmatchesbatting = 0
            var totalinningsbatting = 0
            var totalbattingScore = 0
            var totalUnbeaten = 0
            var highestScore = 0
            var totalAveragesBatting = 0.0
            var totalStrikerate = 0.0
            var totalcenturies = 0
            var totalfifties = 0
            var totalfours = 0
            var totalsixes = 0
            var srcounter = 0

            batting.forEach { battingList ->
                if (battingList!=null){
                    srcounter++
                    if (battingList.matches!=null){
                        totalmatchesbatting+= battingList.matches
                    }
                    if (battingList.innings!=null){
                        totalinningsbatting+= battingList.innings
                    }
                    if (battingList.runs_scored!=null){
                        totalbattingScore+= battingList.runs_scored
                    }
                    if (battingList.not_outs!=null){
                        totalUnbeaten+= battingList.not_outs
                    }
                    if (battingList.highest_inning_score!=null){
                        if (battingList.highest_inning_score>highestScore){
                            highestScore = battingList.highest_inning_score
                        }
                    }
                    if (battingList.average!=null){
                        totalAveragesBatting = battingList.average
                    }
                    totalAveragesBatting /= srcounter
                    if (battingList.strike_rate!=null){
                        totalStrikerate = battingList.strike_rate
                    }
                    totalStrikerate /= srcounter
                    if (battingList.hundreds!=null){
                        totalcenturies += battingList.hundreds
                    }
                    if (battingList.fifties!=null){
                        totalfifties += battingList.fifties
                    }
                    if (battingList.four_x!=null){
                        totalfours += battingList.four_x
                    }
                    if (battingList.six_x!=null){
                        totalsixes += battingList.six_x
                    }

                }
            }
            val odiBatting = Batting(round(totalAveragesBatting),0,totalfifties,totalfours,0.0,0,highestScore,totalcenturies,totalinningsbatting,totalmatchesbatting,totalUnbeaten,totalbattingScore,totalsixes,
                round(totalStrikerate)
            )
            binding.testBattingData = odiBatting

            //bowling
            var srcounter1 = 0
            var totalmatchesbowling = 0
            var totalinningsbowling = 0
            var totalOversbowling = 0.0
            var totalAveragesBowling = 0.0
            var totalEconRate = 0.0
            var totalruns = 0
            var totalwickets = 0
            var totalfivewickets = 0
            bowling.forEach { bowlinglist ->
                if (bowlinglist!=null){
                    srcounter1++
                    if (bowlinglist.matches!=null){
                        totalmatchesbowling+= bowlinglist.matches
                    }
                    if (bowlinglist.innings!=null){
                        totalinningsbowling+= bowlinglist.innings
                    }
                    if (bowlinglist.overs!=null){
                        totalOversbowling+= bowlinglist.overs
                    }
                    if (bowlinglist.average!=null){
                        totalAveragesBowling = bowlinglist.average
                    }
                    if (bowlinglist.econ_rate!=null){
                        totalEconRate = bowlinglist.econ_rate
                    }
                    if (bowlinglist.runs!=null){
                        totalruns += bowlinglist.runs
                    }
                    if (bowlinglist.wickets!=null){
                        totalwickets += bowlinglist.wickets
                    }
                    if (bowlinglist.five_wickets!=null){
                        totalfivewickets += bowlinglist.five_wickets
                    }
                    totalAveragesBowling /= srcounter1
                    totalEconRate /= srcounter1
                }
            }
            val odiBowling = Bowling(round(totalAveragesBowling),
                round(totalEconRate),totalfivewickets,0,totalinningsbowling,totalmatchesbowling, 0,0,totalOversbowling,0.0,totalruns,0.0,0,totalwickets,0)
            binding.testBowlingData = odiBowling
        }
        binding.playersTestBattingShowTV.setOnClickListener {
            if (!isBattingShown){
                isBattingShown = true
                binding.battingLayout.visibility = View.VISIBLE
                binding.playersTestBattingShowTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            }else{
                isBattingShown = false
                binding.battingLayout.visibility = View.GONE
                binding.playersTestBattingShowTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }
        binding.playersTestBowlingShowTV.setOnClickListener {
            if (!isBowlingShown){
                isBowlingShown = true
                binding.bowlingLayout.visibility = View.VISIBLE
                binding.playersTestBowlingShowTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            }else{
                isBowlingShown = false
                binding.bowlingLayout.visibility = View.GONE
                binding.playersTestBowlingShowTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }
    }

}