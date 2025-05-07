package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulalam.cricwave.adapter.RecentMatchAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentBblLeagueBinding
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import java.text.SimpleDateFormat


class BblLeagueFragment : Fragment() {
    private lateinit var binding:FragmentBblLeagueBinding
    private val viewModel: CricketViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBblLeagueBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = RecentMatchAdapter(false,true,)
        binding.bblLeagueRV.adapter = adapter
        binding.bblLeagueRV.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getAllFixtures().observe(viewLifecycleOwner){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val list1 = it.filter { i->i.league_id == 5 }
            Log.d("upcomingfixtures", "upcomingfixtures: ${list1.size}")
            val sortedList = list1.sortedBy { dateFormat.parse(it.starting_at.toString()) }
            adapter.submitList(sortedList.reversed())
        }
    }
}