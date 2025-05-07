package com.mdasrafulalam.cricwave.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.PlayerDetailsViewpagerAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentPlayerDetailsBinding
import com.mdasrafulalam.cricwave.model.playerinfowithcareerbyid.Career
import com.mdasrafulalam.cricwave.model.players.Data
import com.mdasrafulalam.cricwave.retrofit.CricketApi
import com.mdasrafulalam.cricwave.utils.MyConstants
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar


class PlayerDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlayerDetailsBinding
    private var list:List<Career> = listOf()
    private var positionId = 0
    private var position = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPlayerDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.detailsPlayerTotalMOTMTV.visibility = View.GONE
        binding.detailsPlayerTotalMOTSTV.visibility = View.GONE
        val playerInfo = arguments?.getSerializable("playerInfo") as Data
        binding.playerInfo = playerInfo
        MyConstants.CURRENT_PLAYER_ID = playerInfo.id

        //get achievements
        var totalMOTM = 0
        for(i in MyConstants.ALL_FIXTURES.value!!){
            if (i.man_of_match_id==playerInfo.id){
                totalMOTM++
            }
        }
        var totalMOTS = 0
        for(i in MyConstants.ALL_FIXTURES.value!!){
            if (i.man_of_series_id != null){
                if (i.man_of_series_id==playerInfo.id){
                    totalMOTS++
                    Log.d("MOTS", "MOTS: $totalMOTS , ${i.man_of_series_id}")
                }
            }
        }
        //setting achievements
        binding.detailsPlayerTotalMOTMTV.text = String.format("Total MOTM: $totalMOTM")
        binding.detailsPlayerTotalMOTSTV.text = String.format("Total MOTS: $totalMOTS")

        binding.detailsPlayerAchievementTV.setOnClickListener {
            if (binding.detailsPlayerTotalMOTMTV.visibility == View.GONE) {
                binding.detailsPlayerTotalMOTMTV.visibility = View.VISIBLE
                binding.detailsPlayerTotalMOTSTV.visibility = View.VISIBLE
                binding.detailsPlayerAchievementTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.collaps,0)
            } else {
                binding.detailsPlayerTotalMOTMTV.visibility = View.GONE
                binding.detailsPlayerTotalMOTSTV.visibility = View.GONE
                binding.detailsPlayerAchievementTV.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.expand,0)
            }
        }
        Log.d("playerInfo", "playerInfo: $playerInfo")
        //set total run and position
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            try {
                lifecycleScope.launch {
                    val data = CricketApi.retrofitService.getPlayerByIdIncludingCareer(playerInfo.id,"career",MyConstants.API_KEY).data
                    val career = data.career
                    if (career != null) {
                        list = career
                        val positionData = data.position
                        if (positionData != null) {
                            positionId = positionData.id
                            when(positionId){
                                1-> {
                                    position = MyConstants.playerPositionArray[1]
//                                    binding.detailsPlayerPositionTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.bat,0)
                                }
                                2-> {
                                    position = MyConstants.playerPositionArray[2]
//                                    binding.detailsPlayerPositionTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ball,0)
                                }
                                3-> {
                                    position = MyConstants.playerPositionArray[3]
//                                    binding.detailsPlayerPositionTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.gloves,0)
                                }
                                4-> {
                                    position = MyConstants.playerPositionArray[4]
//                                    binding.detailsPlayerPositionTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.allrounder,0)
                                }
                            }
                            binding.detailsPlayerPositionTV.text = position
                            var totalRuns = 0
                            if (list[0].batting!=null){
                                for (i in list){
                                    val runsScored = i.batting?.runs_scored
                                    if (runsScored != null) {
                                        totalRuns += runsScored
                                    }
                                }
                            }
                            var totalWickets = 0
                            if (list[0].bowling!=null){
                                for (i in list){
                                    val wickets = i.bowling?.wickets
                                    if (wickets != null) {
                                        totalWickets += wickets
                                    }
                                }
                            }
                            binding.detailsPlayerTotalRunsTV.text = String.format("Total Runs: $totalRuns")
                            binding.detailsPlayerTotalWicketsTV.text = String.format("Total Wickets: $totalWickets")
                        }
                    }
                }

            }catch (e:Exception){
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.network_conncetion))
                    .setMessage("$e")
                    .setDuration(3000)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }

        }else{
            CookieBar.build(context as Activity?)
                .setTitle("No Active Internet!")
                .setIcon(R.drawable.alert)
                .setBackgroundColor(R.color.swipe_color_4)
                .setCookiePosition(CookieBar.TOP)
                .setDuration(3000) // 3 seconds
                .show()
        }
        //tablayout & viewpager
        val tabLayout = binding.playerDetailsTabLayout
        val viewPage = binding.playersDetailsViewPager
        val tabAdapter = PlayerDetailsViewpagerAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = MyConstants.playerStatsTypeArray[position]
        }.attach()
    }

}