package com.example.fooddonationapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentDashBoardBinding
import com.example.fooddonationapp.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth


class DashBoardFragment : Fragment() {
    private var _binding : FragmentDashBoardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater,container,false)

            binding.button.setOnClickListener {

                FirebaseAuth.getInstance().signOut()
                // authss.signOut()
                findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToOnBoardingFragment())


            }

        return binding.root
        }


}