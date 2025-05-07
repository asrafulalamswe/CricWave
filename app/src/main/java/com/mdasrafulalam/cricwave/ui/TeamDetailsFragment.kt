package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.PlayersAdapter
import com.mdasrafulalam.cricwave.adapter.TeamSquadAdpater
import com.mdasrafulalam.cricwave.adapter.TeamsFixtureAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentTeamDetailsBinding
import com.mdasrafulalam.cricwave.model.teams.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch

class TeamDetailsFragment : Fragment() {

    private lateinit var fixturesAdapter:TeamsFixtureAdapter
    private lateinit var teamSquadAdapter:TeamSquadAdpater
    private lateinit var allPlayersAdpater: PlayersAdapter
    private lateinit var binding: FragmentTeamDetailsBinding
    private var countryWisePlayers:List<com.mdasrafulalam.cricwave.model.players.Data> = listOf()
    private val viewModel:CricketViewModel by activityViewModels()
    private var isFixtureShown = false
    private var isSquadShown = false
    private var isAllPlayersShown = false
    private lateinit var teamDetails:Data
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTeamDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (MyConstants.ALL_PLAYERSWITHTEAM.value?.isEmpty() == true){
            viewModel.getAllPlayersWithTeam().observe(viewLifecycleOwner){
                MyConstants.ALL_PLAYERSWITHTEAM.value = it
            }
        }
        teamDetails = arguments?.getSerializable("teamDetails") as Data
        binding.teamDetails = teamDetails
        Log.d("coutryid", "coutryid: ${teamDetails.country_id}")
        //fixtures
        fixturesAdapter = TeamsFixtureAdapter()
        binding.teamDetailsFixtureRV.adapter = fixturesAdapter
        binding.teamDetailsFixtureRV.layoutManager = LinearLayoutManager(requireContext())
        MyConstants.ALL_FIXTURES.observe(viewLifecycleOwner){
            val filteredFixtureList = it.filter { it.localteam_id == teamDetails.id || it.visitorteam_id==teamDetails.id }
            fixturesAdapter.submitList(filteredFixtureList.reversed())
        }

        binding.teamDetailsFixturesShow.setOnClickListener {
            if (!isFixtureShown){
                binding.teamDetailsFixtureRV.visibility = View.VISIBLE
                isFixtureShown = true
                binding.teamDetailsFixturesShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            }else{
                binding.teamDetailsFixtureRV.visibility = View.GONE
                isFixtureShown = false
                binding.teamDetailsFixturesShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }

        //squad
        teamSquadAdapter = TeamSquadAdpater()
        binding.teamDetailsSquadRV.adapter = teamSquadAdapter
        binding.teamDetailsSquadRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration= DividerItemDecoration(
            binding.teamDetailsSquadRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.teamDetailsSquadRV.addItemDecoration(dividerItemDecoration)
        lifecycleScope.launch {
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                try{
                    val squadlist = viewModel.getSquadWithTeamAndSeasonId(teamDetails.id, 23)
                    teamSquadAdapter.submitList(squadlist)
                }catch (e:Exception){

                }
            }else{
                MyConstants.noNetwork(requireActivity())
            }
        }
        binding.teamDetailsSquadShow.setOnClickListener {
            if (!isSquadShown){
                binding.teamDetailsSquadRV.visibility = View.VISIBLE
                isSquadShown = true
                binding.teamDetailsSquadShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            }else{
                binding.teamDetailsSquadRV.visibility = View.GONE
                isSquadShown = false
                binding.teamDetailsSquadShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }

        //all players
        allPlayersAdpater = PlayersAdapter(false)
        binding.teamDetailsAllPlayersRV.adapter = allPlayersAdpater
        binding.teamDetailsAllPlayersRV.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecorationAllPlayers= DividerItemDecoration(
            binding.teamDetailsAllPlayersRV.context,
            LinearLayoutManager(requireContext()).orientation
        )
        binding.teamDetailsAllPlayersRV.addItemDecoration(dividerItemDecorationAllPlayers)
        getCountryWisePlayers()

        binding.teamDetailsAllPlayerShow.setOnClickListener {
            if (!isAllPlayersShown){
                binding.teamDetailsAllPlayersRV.visibility = View.VISIBLE
                binding.teamDetailsAllPlayersLoading.visibility = View.VISIBLE
                isAllPlayersShown = true
                binding.teamDetailsAllPlayerShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            }else{
                binding.teamDetailsAllPlayersRV.visibility = View.GONE
                binding.teamDetailsAllPlayersLoading.visibility = View.GONE
                isAllPlayersShown = false
                binding.teamDetailsAllPlayerShow.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }
    }

    private fun getCountryWisePlayers(){
        var allplayersId: List<Int>
        try {
            lifecycleScope.launch{
                if (MyConstants.ALL_PLAYERS.value?.isNotEmpty() == true){
                    MyConstants.ALL_PLAYERS.observe(viewLifecycleOwner){
                        allplayersId = it.filter { it.country_id == teamDetails.country_id }.map { it.id }
                        countryWisePlayers = MyConstants.ALL_PLAYERS.value?.filter { allplayersId.contains(it.id) }!!
                        Log.d("allplayersbycountry", "allplayersbycountry: ${countryWisePlayers.size}")
                        binding.teamDetailsAllPlayersLoading.visibility = View.GONE
                        allPlayersAdpater.submitList(countryWisePlayers)
            Log.d("allplayersbycountry", "allplayersbycountry: ${countryWisePlayers.size}")
                    }
                }
            }
        }catch (e:Exception){
            Log.d("allplayersbycountry", "allplayersbycountry: $e")
        }
    }
}