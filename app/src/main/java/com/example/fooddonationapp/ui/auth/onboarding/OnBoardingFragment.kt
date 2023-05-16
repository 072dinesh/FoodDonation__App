package com.example.fooddonationapp.ui.auth.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.utils.Constant
import com.example.fooddonationapp.utils.PrefManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class OnBoardingFragment : Fragment() {

    private var _binding : FragmentOnBoardingBinding ? = null
    private val binding get() = _binding!!
    private lateinit var adapter : OnBoardingAdapter
    private var isBtnEnabled = false
    private var emailnago : String?=null
    private var emaildonor: String?=null

    private lateinit var dbNgo : FirebaseFirestore
    private lateinit var dbDonar : FirebaseFirestore

    lateinit var auths: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        auths= FirebaseAuth.getInstance()


        dbNgo = FirebaseFirestore.getInstance()
        dbDonar = FirebaseFirestore.getInstance()


        //database_store()
        CheckUserLogin()

        return binding.root
    }


    private fun CheckUserLogin() {
         var data =  PrefManager.getString(PrefManager.ACCESS_TOKEN,"email")
        if (auths.currentUser != null)
        {
                dbDonar.collection("Donar")
                    .get()
                    .addOnSuccessListener { documents ->

                        for (document in documents )
                        {
                            emaildonor = document.get("email").toString()
                            if(data.equals(emaildonor)) {

                                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToDonorDashBoardFragment())
                                // Log.e("exit", "DocumentSnapshot data: ${document.get("email")}")

                                Log.e("emails", "DocumentSnapshot data: ${emaildonor}")
                            }
                        }

                    }

            dbNgo.collection("NGO")
                .get()
                .addOnSuccessListener { documents ->

                    for (document in documents )
                    {
                        emailnago = document.get("email").toString()
                        if(data.equals(emailnago)) {

                            Log.e("exit", "DocumentSnapshot data: ${document.get("email")}")

                            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToNgoDashBoardFragment())
                        }
                    }

                }

        }
        else {
            setUpUi()
            setOnClicks()

        }


    }

    private fun setUpUi(){
        Timber.e("setupui")
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
            PrefManager.setBoolean(Constant.IS_INTRO_COMPLETE,true)
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