package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.TournamentFixtureAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentTournamentFixturesBinding
import com.mdasrafulalam.cricwave.utils.MyConstants

class TournamentFixturesFragment : Fragment() {
    private lateinit var adapter: TournamentFixtureAdapter
    private lateinit var binding: FragmentTournamentFixturesBinding
    private var stageId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTournamentFixturesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dest =  arguments?.getString("last_dest")
        stageId = arguments?.getInt("stage_id")!!
        Log.d("stageidindetils", "stageId $stageId")
        if (dest=="Series"){
            adapter = TournamentFixtureAdapter(
                isFromHome = false,
                isFromSeries = true,
                isFromBookMark = false
            )
            setAdapter()
        }else if (dest=="Home"){
            adapter = TournamentFixtureAdapter(
                isFromHome = true,
                isFromSeries = false,
                isFromBookMark = false
            )
            setAdapter()
        }else if (dest=="BookMark"){
            adapter = TournamentFixtureAdapter(
                isFromHome = true,
                isFromSeries = false,
                isFromBookMark = true
            )
            setAdapter()
        }

        binding.backbFAB.setOnClickListener {
            if (dest=="Home"){
                findNavController().navigate(R.id.action_tournamentFixturesFragment_to_homeFragment)
            }else if (dest=="Series"){
                findNavController().navigate(R.id.action_tournamentFixturesFragment_to_seriesFragment)
            }else if (dest=="BookMark"){
                findNavController().navigate(R.id.action_tournamentFixturesFragment_to_bookMarkFragment)
            }
        }
        binding.tournamentFixturesSRL.setOnRefreshListener {
            binding.tournamentFixturesSRL.isRefreshing = false
        }


    }
    private fun setAdapter(){
        binding.tournamentFixturesRV.adapter = adapter
        binding.tournamentFixturesRV.layoutManager = LinearLayoutManager(requireContext())
        MyConstants.ALL_FIXTURES.observe(viewLifecycleOwner){ it ->
            val filteredList = it.filter { it.stage_id == stageId }
            Log.d("dataoffixtures", "FixturesTournament: ${filteredList.size}")
            adapter.submitList(filteredList.reversed())
        }
    }
}