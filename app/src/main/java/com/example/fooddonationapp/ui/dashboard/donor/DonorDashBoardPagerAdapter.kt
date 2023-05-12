package com.example.fooddonationapp.ui.dashboard.donor

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fooddonationapp.ui.tabs.donor.history.HistoryDonorTabFragment
import com.example.fooddonationapp.ui.tabs.donor.recent.RecentDonorTabFragment

class DonorDashBoardPagerAdapter(fa:Fragment):FragmentStateAdapter(fa){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecentDonorTabFragment()
            else -> HistoryDonorTabFragment()
        }
    }
}