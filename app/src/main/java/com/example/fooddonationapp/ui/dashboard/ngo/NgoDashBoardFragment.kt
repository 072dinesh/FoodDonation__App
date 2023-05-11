package com.example.fooddonationapp.ui.dashboard.ngo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentNgoDashBoardBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class NgoDashBoardFragment : Fragment() {

private  var _binding : FragmentNgoDashBoardBinding ? = null
    private val binding get() = _binding!!

    private val tabTitle = arrayListOf("Recent","History")
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNgoDashBoardBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()


        setUpUi()
        setonclick()
        return binding.root
    }
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    private fun setUpUi(){
        binding.vpNgoDashBoard.adapter = NgoDashBoardPagerAdapter(this)
//        TabLayoutMediator(binding.tlNgoDashBoard,binding.vpNgoDashBoard){tab,position ->
//            tab.text = tabTitle[position]
//        }.attach()
//        for (i in 0..2){
//            val textview = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title,null)
//            as TextView
//            binding.tlNgoDashBoard.getTabAt(i)?.customView = textview
//        }
            binding.btnDashBoardNgoHistoryTab.setOnClickListener {
            binding.vpNgoDashBoard.currentItem = 1
            binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardNgoHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
            binding.btnDashBoardNgoRecentTab.isSelected = false

            //binding.btnDashBoardNgoRecentTab.isPressed = false
           // binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)
            //binding.btnDashBoardNgoRecentTab.background = requireContext().getDrawable(R.drawable.btn_not_press)
        }
        binding.btnDashBoardNgoRecentTab.setOnClickListener {
            binding.vpNgoDashBoard.currentItem = 0

            binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
            binding.btnDashBoardNgoHistoryTab.isSelected = false
          // binding.btnDashBoardNgoHistoryTab.isPressed = false
            //binding.btnDashBoardNgoHistoryTab.background = requireContext().getDrawable()
           // binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
        }
     binding.vpNgoDashBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    Log.e("Log","Onselsct")
                    binding.btnDashBoardNgoHistoryTab.isSelected = false
                    binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardNgoHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                    //binding.btnDashBoardNgoHistoryTab.setTextAppearance(R.style.Button_Tabs_diasable)
                    //binding.btnDashBoardNgoHistoryTab.isPressed = false
                    //binding.btnDashBoardNgoHistoryTab.background = requireContext().getDrawable(R.drawable.btn_not_press)
                    //binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)


                }
                else{
                    binding.btnDashBoardNgoRecentTab.isSelected = false
                    binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                   // binding.btnDashBoardNgoRecentTab.setTextAppearance(R.style.Button_Tabs_diasable)
                    //binding.btnDashBoardNgoRecentTab.isPressed = false
                    //binding.btnDashBoardNgoRecentTab.background = requireContext().getDrawable(R.drawable.btn_not_press)
                    //binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)


                }
                super.onPageSelected(position)
            }
        })
//        binding.vpNgoDashBoard.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpNgoDashBoard.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

        })
    }
    private fun setonclick(){
        binding.ivDashBoardNgoLogOut.setOnClickListener {
             auth.signOut()
            findNavController().navigate(NgoDashBoardFragmentDirections.actionNgoDashBoardFragmentToOnBoardingFragment())


        }
    }

}