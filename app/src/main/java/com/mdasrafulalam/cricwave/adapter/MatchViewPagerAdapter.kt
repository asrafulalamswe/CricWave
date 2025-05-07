package com.mdasrafulalam.cricwave.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mdasrafulalam.cricwave.ui.LiveMatchesFragment
import com.mdasrafulalam.cricwave.ui.RecentMatchesFragment
import com.mdasrafulalam.cricwave.ui.UpcomingMatchesFragment

class MatchViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUMTABS = 3
    override fun getItemCount(): Int {
        return NUMTABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LiveMatchesFragment()
            }
            1 -> {
                UpcomingMatchesFragment()
            }
            else -> {
                RecentMatchesFragment()
            }
        }
    }
}