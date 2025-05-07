package com.mdasrafulalam.cricwave.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.adapter.PopularT20ViewPagerAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentT20LeaguesBinding
import com.mdasrafulalam.cricwave.utils.MyConstants


class T20LeaguesFragment : Fragment() {
   private lateinit var binding:FragmentT20LeaguesBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentT20LeaguesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = binding.popularT20LeagusTabLayout
        viewPager = binding.popularT20LeagusViewPager
        val tabAdapter = PopularT20ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = tabAdapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabIconTint = null
        tabLayout.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = MyConstants.popularT20TabArray[position]
        }.attach()

        binding.backbFAB.setOnClickListener {
            findNavController().navigate(R.id.action_t20LeaguesFragment_to_moreFragment)
        }
    }
}