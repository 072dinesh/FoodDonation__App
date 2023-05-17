package com.example.fooddonationapp.ui.dashboard.ngo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentNgoDashBoardBinding
import com.example.fooddonationapp.utils.PrefManager
import com.example.fooddonationapp.ui.tabs.ngo.recent.requsetform.RequestFormFragment
import com.example.fooddonationapp.utils.Constant
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import kotlin.concurrent.fixedRateTimer


class NgoDashBoardFragment : Fragment() {

private  var _binding : FragmentNgoDashBoardBinding ? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    private val tabTitle = arrayListOf("Recent","History")
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNgoDashBoardBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        var data =  PrefManager.getString(PrefManager.ACCESS_TOKEN)
        Timber.e("Data Hsred Login",data)
        Timber.e(auth.currentUser?.email.toString())
        setUpUi()
        setonclick()
        return binding.root
    }
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    private fun setUpUi(){

        binding.vpNgoDashBoard.adapter = NgoDashBoardPagerAdapter(this)

            binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
            binding.btnDashBoardNgoHistoryTab.setOnClickListener {
            binding.vpNgoDashBoard.currentItem = 1
            binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardNgoHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_1))
            binding.btnDashBoardNgoRecentTab.isSelected = false


        }
        binding.btnDashBoardNgoRecentTab.setOnClickListener {
            binding.vpNgoDashBoard.currentItem = 0

            binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
            binding.btnDashBoardNgoHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_1))
            binding.btnDashBoardNgoHistoryTab.isSelected = false

        }
     binding.vpNgoDashBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")

            override fun onPageSelected(position: Int) {
                if(position == 0) {
                    Timber.tag("Log").e("Onselsct")
                    binding.btnDashBoardNgoHistoryTab.isSelected = false
                    binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardNgoRecentTab.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.purple_light
                        )
                    )
                    binding.btnDashBoardNgoHistoryTab.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.purple_1
                        )
                    )


                }
                else{
                    binding.btnDashBoardNgoRecentTab.isSelected = false
                    binding.btnDashBoardNgoRecentTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardNgoHistoryTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardNgoHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                    binding.btnDashBoardNgoRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_1))


                }
                super.onPageSelected(position)
            }
        })

    }



    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()

        binding.vpNgoDashBoard.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

        })
    }
    private fun setonclick(){
        binding.ivDashBoardNgoLogOut.setOnClickListener {
             auth.signOut()
            PrefManager.clearAll()
            findNavController().navigate(NgoDashBoardFragmentDirections.actionNgoDashBoardFragmentToOnBoardingFragment())


        }
    }


}