package com.example.fooddonationapp.ui.tabs.ngo.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentLoginBinding
import com.example.fooddonationapp.databinding.FragmentRecentNgoTabBinding


class RecentNgoTabFragment : Fragment() {

    private var _binding : FragmentRecentNgoTabBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentNgoTabBinding.inflate(inflater,container,false)
        setOnClick()
        return binding.root
    }

    private fun setOnClick(){
        binding.flbAddRequest.setOnClickListener {
            findNavController().navigate(RecentNgoTabFragmentDirections.actionRecentNgoTabFragmentToRequestFormFragment2())
        }
    }

}