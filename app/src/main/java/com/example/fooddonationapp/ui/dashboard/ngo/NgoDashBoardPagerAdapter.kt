package com.example.fooddonationapp.ui.dashboard.ngo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fooddonationapp.ui.tabs.ngo.history.HistoryNgoTabFragment
import com.example.fooddonationapp.ui.tabs.ngo.recent.RecentNgoTabFragment

class NgoDashBoardPagerAdapter(fa : Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecentNgoTabFragment()
            else -> HistoryNgoTabFragment()
        }
    }
}