package com.mdasrafulalam.cricwave.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.SeriesViewPagerAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentSeriesBinding
import com.mdasrafulalam.cricwave.utils.MyConstants


class SeriesFragment : Fragment() {
    private lateinit var binding: FragmentSeriesBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = binding.seriesTabLayout
        viewPager = binding.seriesViewPager
        val tabAdapter = SeriesViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = tabAdapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabIconTint = null
        tabLayout.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = MyConstants.seriesTabArray[position]
            when (position) {
                0 -> {
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.team)
                    drawable!!.setTint(Color.TRANSPARENT)
                    drawable.clearColorFilter()
                    tab.icon = drawable
                }
                1 -> {
                    val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.tournament)
                    drawable!!.setTint(Color.TRANSPARENT)
                    drawable.clearColorFilter()
                    tab.icon = drawable
                }
            }
        }.attach()
    }


}