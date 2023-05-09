package com.example.fooddonationapp.ui.auth.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.utils.Constant
import com.example.fooddonationapp.utils.PrefManager
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : Fragment() {

    private var _binding : FragmentOnBoardingBinding ? = null
    private val binding get() = _binding!!
    private lateinit var adapter : OnBoardingAdapter
    private var isBtnEnabled = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        setUpUi()
        setOnClicks()
        return binding.root
    }
    private fun setUpUi(){

        adapter = OnBoardingAdapter(requireActivity())
        binding.vpOnBoarding.adapter = adapter
        TabLayoutMediator(binding.tlOnBoarding,binding.vpOnBoarding){_,_ ->

        }.attach()
        binding.vpOnBoarding.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 2){
                    isBtnEnabled = true
                }
                binding.btnContinueOnBoarding.isEnabled = isBtnEnabled
            }
        }
        )
    }
    private fun setOnClicks(){
        binding.btnContinueOnBoarding.setOnClickListener {
          //  PrefManager.setBoolean(Constant.IS_INTRO_COMPLETE,true)
            findNavController().navigate(
                OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}