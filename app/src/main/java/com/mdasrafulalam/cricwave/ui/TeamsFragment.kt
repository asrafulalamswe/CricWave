package com.mdasrafulalam.cricwave.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.adapter.TeamsAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentTeamsBinding
import com.mdasrafulalam.cricwave.model.teams.Data
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel


class TeamsFragment : Fragment() {
    private val viewModel: CricketViewModel by activityViewModels()
    private lateinit var internationalTeamsAdapter: TeamsAdapter
    private lateinit var leaguesTeamAdapter: TeamsAdapter
    private lateinit var binding: FragmentTeamsBinding
    private  var list = emptyList<Data>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTeamsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get data
        if (MyConstants.ALL_TEAMS.value?.isNotEmpty() == true){
            MyConstants.ALL_TEAMS.observe(viewLifecycleOwner){
                list = it
            }
        }else{
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                //viewmodel call to get data
            }else{
                MyConstants.noNetwork(requireActivity())
            }
        }
        //international
        internationalTeamsAdapter = TeamsAdapter{
            loadMoreDataInternational()
        }
        binding.teamsIntRV.adapter = internationalTeamsAdapter
        binding.teamsIntRV.layoutManager = LinearLayoutManager(requireContext())
        val filteredDataInt = list.filter { it.national_team }
        val limitedDataInt = filteredDataInt.take(5)
        internationalTeamsAdapter.submitList(limitedDataInt)
        //        leagues
        leaguesTeamAdapter = TeamsAdapter{
            loadMoreDataLeagues()
        }
        binding.teamsLeaguesRV.adapter = leaguesTeamAdapter
        binding.teamsLeaguesRV.layoutManager = LinearLayoutManager(requireContext())
        val filteredDataleagues = list.filter { !it.national_team }
        val limitedDataLeagues = filteredDataleagues.take(5)
        leaguesTeamAdapter.submitList(limitedDataLeagues)


    }
    override fun onResume() {
        super.onResume()
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            //international
            val filteredDataInt = list.filter { it.national_team }
            val limitedDataInt = filteredDataInt.take(5)
            internationalTeamsAdapter.submitList(limitedDataInt)
            //leagues
            val filteredDataleagues = list.filter { !it.national_team }
            val limitedDataLeagues = filteredDataleagues.take(5)
            leaguesTeamAdapter.submitList(limitedDataLeagues)
        }
    }
    override fun onPause() {
        super.onPause()
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            //international
            val filteredDataInt = list.filter { it.national_team }
            val limitedDataInt = filteredDataInt.take(5)
            internationalTeamsAdapter.submitList(limitedDataInt)
            //leagues
            val filteredDataleagues = list.filter { !it.national_team }
            val limitedDataLeagues = filteredDataleagues.take(5)
            leaguesTeamAdapter.submitList(limitedDataLeagues)
        }
    }
    private fun loadMoreDataInternational(){
        val filteredData = list.filter { it.national_team }
        internationalTeamsAdapter.submitList(filteredData)
    }
    private fun loadMoreDataLeagues(){
        val filteredData = list.filter { !it.national_team }
        leaguesTeamAdapter.submitList(filteredData)
    }
}