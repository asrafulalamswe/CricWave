package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.UpcomingMatchAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentUpcomingBinding
import com.mdasrafulalam.cricwave.utils.MyConstants
import com.mdasrafulalam.cricwave.viewmomdel.CricketViewModel
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar
import java.text.SimpleDateFormat

class UpcomingMatchesFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: CricketViewModel by activityViewModels()
    private lateinit var adapter: UpcomingMatchAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        swipeRefreshLayout = binding.upcomingMatchesSRL
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = UpcomingMatchAdapter()
        binding.upcomingMatchesRV.adapter = adapter
        binding.upcomingMatchesRV.setHasFixedSize(true)
        binding.upcomingMatchesRV.layoutManager = LinearLayoutManager(requireContext())
        MyConstants.ALL_UpcomingFIXTURES.observe(viewLifecycleOwner){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val list1 = MyConstants.ALL_UpcomingFIXTURES.value
            Log.d("upcomingfixtures", "upcomingfixtures: ${list1?.size}")
            val sortedList = list1?.sortedBy { dateFormat.parse(it.starting_at.toString()) }
            adapter.submitList(sortedList)
        }
        viewModel.getAllFixtures().observe(viewLifecycleOwner){
            MyConstants.ALL_UpcomingFIXTURES.value = it.filter { i-> i.status=="NS" }
        }

        if (MyConstants.ALL_UpcomingFIXTURES.value?.isEmpty() == true){
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                lifecycleScope.launch {
                    try {
                        Log.d("upcomingfixtures", "upcomingfixtures: Entered ")
                        MyConstants.ALL_UpcomingFIXTURES.value = viewModel.getFixturesFilteringDateIncludeRuns("2023-02-19,2023-03-30")
                    }catch (e:Exception){
                        Log.d("upcomingfixtures", "upcomingfixtures: $e")
                    }
                }

            }else{
                MyConstants.noNetwork(requireActivity())
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if (MyConstants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity()).setMessage("Fixtures Updated!").setDuration(3000)
                    .setBackgroundColor(R.color.color_tab_text).setIcon(R.drawable.success)
                    .setMessageColor(R.color.white)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                MyConstants.noNetwork(requireActivity())
            }
        }
    }



}