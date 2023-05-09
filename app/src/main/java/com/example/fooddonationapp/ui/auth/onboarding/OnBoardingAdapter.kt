package com.example.fooddonationapp.ui.auth.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fooddonationapp.R

private const val NUM_PAGES = 3
class OnBoardingAdapter(
    private val fa : FragmentActivity
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int  = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            OnBoardingViewPagerFragment(
                fa.getString(R.string.on_boarding_1),
                R.drawable.onboarding_1
            )
        }
        else if (position == 1){
            OnBoardingViewPagerFragment(
                fa.getString(R.string.on_boarding_2),
                R.drawable.onboarding_1
            )
        }
        else{
            OnBoardingViewPagerFragment(
                fa.getString(R.string.on_boarding_3),
                R.drawable.onboarding_1
            )
        }
    }
}