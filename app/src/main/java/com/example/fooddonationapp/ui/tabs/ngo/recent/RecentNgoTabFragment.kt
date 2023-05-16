package com.example.fooddonationapp.ui.tabs.ngo.recent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentLoginBinding
import com.example.fooddonationapp.databinding.FragmentRecentNgoTabBinding
import com.example.fooddonationapp.ui.tabs.ngo.recent.requsetform.RequestFormFragment
import com.example.fooddonationapp.utils.Constant
import timber.log.Timber


class RecentNgoTabFragment : Fragment() {

    private var _binding : FragmentRecentNgoTabBinding ?= null
    private val binding get() = _binding!!
    var requestFormBrodcaster : LocalBroadcastManager ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentNgoTabBinding.inflate(inflater,container,false)
        setUpUi()
        setOnClick()
        return binding.root
    }

    private fun setUpUi(){
        requestFormBrodcaster = LocalBroadcastManager.getInstance(requireContext())
    }
    private fun setOnClick(){
        binding.flbAddRequest.setOnClickListener {
            Timber.e("Click","Clickckckckckkc")
           val intent = Intent(Constant.BROADCAST_RECEIVER.REQUEST_FORM)
            requestFormBrodcaster?.sendBroadcast(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}