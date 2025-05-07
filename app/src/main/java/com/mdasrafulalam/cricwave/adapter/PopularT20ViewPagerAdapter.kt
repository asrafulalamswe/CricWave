package com.mdasrafulalam.cricwave.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mdasrafulalam.cricwave.ui.*

class PopularT20ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle)  {
    private val NUMTABS = 3
    override fun getItemCount(): Int {
        return NUMTABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                T20ILeagueFragment()
            }
            1->{
                BblLeagueFragment()
            }
            else -> {
                CsaT20LeagueFragment()
            }
        }
    }
}