package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.cricwave.adapter.RankViewPagerAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentRankingBinding
import com.mdasrafulalam.cricwave.utils.MyConstants

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = binding.rankTabLayout
        viewPager = binding.rankViewPager
        val tabAdapter = RankViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = tabAdapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabIconTint = null
        tabLayout.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = MyConstants.rankTypeTabArray[position]
        }.attach()
    }
}