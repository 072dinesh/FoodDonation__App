package com.example.fooddonationapp.ui.dashboard.ngo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private fun setUpUi(){
        binding.vpNgoDashBoard.adapter = NgoDashBoardPagerAdapter(this)
        TabLayoutMediator(binding.tlNgoDashBoard,binding.vpNgoDashBoard){tab,position ->
            tab.text = tabTitle[position]
        }.attach()
        for (i in 0..2){
            val textview = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title,null)
            as TextView
            binding.tlNgoDashBoard.getTabAt(i)?.customView = textview
        }
    }
    private fun setonclick(){
        binding.ivDashBoardNgoLogOut.setOnClickListener {
             auth.signOut()
            findNavController().navigate(NgoDashBoardFragmentDirections.actionNgoDashBoardFragmentToOnBoardingFragment())


        }
    }

}