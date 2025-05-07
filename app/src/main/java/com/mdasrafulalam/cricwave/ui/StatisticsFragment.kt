package com.mdasrafulalam.cricwave.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.StatsiticsStanadingAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentStatisticsBinding
import com.mdasrafulalam.cricwave.model.standings.AllStandings
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private var allStandings:List<AllStandings> = listOf()
    private var updateStandings:List<AllStandings> = listOf()
    private val viewModel: CricketViewModel by activityViewModels()
    private lateinit var adapter:StatsiticsStanadingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(layoutInflater,container,false)
        viewModel.getAllTeams().observe(viewLifecycleOwner){
            MyConstants.ALL_TEAMS.value = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bblleagueStat.setBackgroundColor(resources.getColor(R.color.color_tab_text))
        adapter = StatsiticsStanadingAdapter()
        binding.statisticsRV.adapter = adapter
        binding.statisticsRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration1 = DividerItemDecoration(
            binding.statisticsRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.statisticsRV.addItemDecoration(dividerItemDecoration1)
        viewModel.getAllStandingsBySeason().observe(viewLifecycleOwner){
            allStandings = it
            val standingsBySeason = allStandings
                .filter { it.league_id == 5 }
                .distinctBy { "${it.team_id}_${it.season_id}" }
                .groupBy { it.season_id }
                .mapValues { (_, standings) ->
                    standings.sortedBy { it.position }
                }

            updateStandings = standingsBySeason
                .entries
                .sortedByDescending { it.key }
                .flatMap { (_, standings) -> standings.sortedBy { it.position } }

            adapter.submitList(updateStandings)
        }
        binding.bblleagueStat.setOnClickListener {
            binding.t20IleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.csaleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.bblleagueStat.setBackgroundColor(resources.getColor(R.color.color_tab_text))
            val standingsBySeason = allStandings
                .filter { it.league_id == 5 }
                .distinctBy { "${it.team_id}_${it.season_id}" }
                .groupBy { it.season_id }
                .mapValues { (_, standings) ->
                    standings.sortedBy { it.position }
                }

            updateStandings = standingsBySeason
                .entries
                .sortedByDescending { it.key }
                .flatMap { (_, standings) -> standings.sortedBy { it.position } }
            adapter.submitList(updateStandings)
        }
        binding.csaleagueStat.setOnClickListener {
            binding.t20IleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.bblleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.csaleagueStat.setBackgroundColor(resources.getColor(R.color.color_tab_text))
            val standingsBySeason = allStandings
                .filter { it.league_id == 10 }
                .distinctBy { "${it.team_id}_${it.season_id}" }
                .groupBy { it.season_id }
                .mapValues { (_, standings) ->
                    standings.sortedBy { it.position }
                }

            updateStandings = standingsBySeason
                .entries
                .sortedByDescending { it.key }
                .flatMap { (_, standings) -> standings.sortedBy { it.position } }
            adapter.submitList(updateStandings)
        }
        binding.t20IleagueStat.setOnClickListener {
            binding.csaleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.bblleagueStat.setBackgroundColor(Color.TRANSPARENT)
            binding.t20IleagueStat.setBackgroundColor(resources.getColor(R.color.color_tab_text))
            val standingsBySeason = allStandings
                .filter { it.league_id == 3 }
                .distinctBy { "${it.team_id}_${it.season_id}" }
                .groupBy { it.season_id }
                .mapValues { (_, standings) ->
                    standings.sortedBy { it.position }
                }
            updateStandings = standingsBySeason
                .entries
                .sortedByDescending { it.key }
                .flatMap { (_, standings) -> standings.sortedBy { it.position } }
            adapter.submitList(updateStandings)
        }
        binding.backbFAB.setOnClickListener {
            findNavController().navigate(R.id.action_statisticsFragment_to_moreFragment)
        }
    }
}