package com.example.fooddonationapp.ui.auth.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import coil.load
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.databinding.FragmentOnBoardingViewPagerBinding

class OnBoardingViewPagerFragment(
    private val title : String,
    @DrawableRes private val imageRes : Int
) : Fragment() {

    private var _binding : FragmentOnBoardingViewPagerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingViewPagerBinding.inflate(inflater,container,false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi(){
        binding.tvOnBoardingTxt.text = title
        binding.ivOnBoardingImage.load(imageRes){
            crossfade(false)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}