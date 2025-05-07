package com.mdasrafulalam.cricwave.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mdasrafulalam.cricwave.ui.*

class PlayerDetailsViewpagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUMTABS = 3
    override fun getItemCount(): Int {
        return NUMTABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return PlayersDetailsT20Fragment()
            }
            1 -> {
                return PlayersDetailsOdiFragment()
            }
            else -> {
                return PlayersDetailsTestFragment()
            }
        }
    }

}