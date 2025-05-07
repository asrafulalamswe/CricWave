package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.cricwave.adapter.MatchViewPagerAdapter
import com.mdasrafulalam.cricwave.databinding.FragmentMatchesBinding
import com.mdasrafulalam.cricwave.utils.MyConstants


class MatchesFragment : Fragment() {
    private lateinit var binding: FragmentMatchesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMatchesBinding.inflate(layoutInflater, container, false)
        MyConstants.IS_FROM_VIEWPAGER = true
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.matchTabLayout
        val viewPage = binding.matchViewPager
        val tabAdapter = MatchViewPagerAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabMode = TabLayout.MODE_FIXED

        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = MyConstants.matchTypeArray[position]
        }.attach()
    }

    override fun onDetach() {
        super.onDetach()
        MyConstants.IS_FROM_VIEWPAGER = false
    }
}