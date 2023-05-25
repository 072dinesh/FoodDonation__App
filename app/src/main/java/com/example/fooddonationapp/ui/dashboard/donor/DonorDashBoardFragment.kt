package com.example.fooddonationapp.ui.dashboard.donor

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentDonorDashBoardBinding
import com.example.fooddonationapp.ui.tabs.donor.history.HistoryDonorTabFragment
import com.example.fooddonationapp.ui.tabs.donor.recent.RecentDonorTabFragment
import com.example.fooddonationapp.ui.tabs.ngo.history.HistoryNgoTabFragment
import com.example.fooddonationapp.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class DonorDashBoardFragment : Fragment() {
    private var _binding :FragmentDonorDashBoardBinding? = null
    private val binding get() = _binding!!
    private val tabTitle = arrayListOf("Recent","History")
    lateinit var auth: FirebaseAuth
    var emaildonor : String?=null
    private lateinit var dbdonar : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorDashBoardBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        Timber.e(auth.currentUser?.email.toString())

        dbdonar = FirebaseFirestore.getInstance()
        setUpUi()
        setonclick()
        return binding.root
    }

    private fun setUpUi(){
        var data =  PrefManager.getString(PrefManager.ACCESS_TOKEN)
        if (data.toString() == "Donor"){


            dbdonar.collection("Donar").get().addOnSuccessListener {documents ->

                for (document in documents )
                {
                    emaildonor = document.get("email").toString()
                    Log.e("emails", "DocumentSnapshot data: ${emaildonor}")

                    if (auth.currentUser?.email.toString() == emaildonor){

                        Timber.e(document.get("username").toString())
                        binding.tvCardDonorDashboard.text = document.get("username").toString()

                    }
                }

            }
        }
        binding.vpDonorDashBoard.adapter = DonorDashBoardPagerAdapter(this)
        binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primaryContainer))
        binding.btnDashBoardDonorHistoryTab.setOnClickListener {
            binding.vpDonorDashBoard.currentItem = 1
            binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primaryContainer))
            binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primary))
            binding.btnDashBoardDonorRecentTab.isSelected=false
           //fragmentManager?.beginTransaction()?.detach(HistoryDonorTabFragment())?.attach(RecentDonorTabFragment())?.commit()
        }
            binding.btnDashBoardDonorRecentTab.setOnClickListener {
            binding.vpDonorDashBoard.currentItem = 0
            binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
            binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_press)
            binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primaryContainer))
            binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.md_theme_light_primary))
            binding.btnDashBoardDonorHistoryTab.isSelected=false
             //  fragmentManager?.beginTransaction()?.detach(RecentDonorTabFragment())?.attach(HistoryDonorTabFragment())?.commit()
        }

        binding.vpDonorDashBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    Log.e("Log","Onselect")

                    binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_primaryContainer))
                    binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_primary))
                    binding.btnDashBoardDonorHistoryTab.isSelected = false
                    //refreshFragments(HistoryNgoTabFragment())
                   // fragmentManager?.beginTransaction()?.detach(RecentDonorTabFragment())?.attach(HistoryDonorTabFragment())?.commit()
                }
                else{

                    binding.btnDashBoardDonorRecentTab.setBackgroundResource(R.drawable.btn_not_press)
                    binding.btnDashBoardDonorHistoryTab.setBackgroundResource(R.drawable.btn_press)
                    binding.btnDashBoardDonorHistoryTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_primaryContainer))
                    binding.btnDashBoardDonorRecentTab.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_primary))
                    binding.btnDashBoardDonorRecentTab.isSelected = false
                    //fragmentManager?.beginTransaction()?.detach(HistoryDonorTabFragment())?.attach(RecentDonorTabFragment())?.commit()
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
        binding.tvDashBoardProfile.setOnClickListener {
            findNavController().navigate(DonorDashBoardFragmentDirections.actionDonorDashBoardFragmentToProfileFragment2())
        }

    }
//         fun refreshFragments(fragment: Fragment) {
//        binding.vpDonorDashBoard.setAdapter(DonorDashBoardPagerAdapter(fragment))
//    }

}