package com.example.fooddonationapp.ui.dashboard.donor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentDashBoardBinding
import com.example.fooddonationapp.databinding.FragmentDonorDashBoardBinding
import com.google.firebase.auth.FirebaseAuth


class DonorDashBoardFragment : Fragment() {
    private var _binding :FragmentDonorDashBoardBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorDashBoardBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()
        binding.loout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            auth.signOut()
            findNavController().navigate(DonorDashBoardFragmentDirections.actionDonorDashBoardFragmentToOnBoardingFragment())


        }

        return binding.root
    }

}