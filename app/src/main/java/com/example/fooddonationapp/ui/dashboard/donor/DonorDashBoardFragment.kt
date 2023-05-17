package com.example.fooddonationapp.ui.dashboard.donor

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentDonorDashBoardBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber


class DonorDashBoardFragment : Fragment() {
    private var _binding :FragmentDonorDashBoardBinding? = null
    private val binding get() = _binding!!
    private val tabTitle = arrayListOf("Recent","History")
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorDashBoardBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        Timber.e(auth.currentUser?.email.toString())
        setUpUi()
        setonclick()
        return binding.root
    }

    private fun setUpUi(){

        binding.vpDonorDashBoard.adapter = DonorDashBoardPagerAdapter(this)
        binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_light))
        binding.btnDashBoardDonorHistoryTab.setOnClickListener {
            binding.vpDonorDashBoard.currentItem = 1
            binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_light))
            binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_1))
            binding.btnDashBoardDonorRecentTab.isSelected=false
        }
            binding.btnDashBoardDonorRecentTab.setOnClickListener {
            binding.vpDonorDashBoard.currentItem = 0
            binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_light))
            binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_1))
            binding.btnDashBoardDonorHistoryTab.isSelected=false
        }

        binding.vpDonorDashBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    Log.e("Log","Onselect")
                    binding.btnDashBoardDonorHistoryTab.isSelected = false
                    binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                    binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_1))
                }
                else{
                    binding.btnDashBoardDonorRecentTab.isSelected = false
                    binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_light))
                    binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_1))
                }
                super.onPageSelected(position)
            }
        })

    }
    override fun onDestroy() {
        super.onDestroy()
        binding.vpDonorDashBoard.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

        })
    }
    private fun setonclick(){
        binding.tvDashBoardDonorLogout.setOnClickListener {
            auth.signOut()
            PrefManager.clearAll()

            findNavController().navigate(DonorDashBoardFragmentDirections.actionDonorDashBoardFragmentToOnBoardingFragment())
        }
    }

}