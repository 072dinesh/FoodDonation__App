package com.example.fooddonationapp.ui.auth.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.ui.auth.login.LoginFragmentDirections
import com.example.fooddonationapp.ui.auth.registration.Donor
import com.example.fooddonationapp.utils.Constant
import com.example.fooddonationapp.utils.PrefManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class OnBoardingFragment : Fragment() {

    private var _binding : FragmentOnBoardingBinding ? = null
    private val binding get() = _binding!!
    private lateinit var adapter : OnBoardingAdapter
    private var isBtnEnabled = false
    lateinit var emailnago : String
    lateinit var emaildonor: String
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference

    lateinit var auths: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        auths= FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("NGO")
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Donor")


        CheckUserLogin()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    var userdata = data.getValue(Donor::class.java)
                    emailnago = userdata?.email.toString()
                    Timber.e("=====", auths.currentUser?.email.toString())

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")


            }
        })
        databaseReference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    var userdata = data.getValue(Donor::class.java)
                    emaildonor = userdata?.email.toString()
                    Timber.e("=====", auths.currentUser?.email.toString())

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")


            }
        })

        return binding.root
    }




    private fun CheckUserLogin() {
        var listofemployees = ArrayList<Donor>()
//        Timber.e("=====" + auths.currentUser?.uid)



        if (auths.currentUser != null) {
            Timber.e("=====", auths.currentUser?.email.toString())

            if (auths.currentUser?.email.toString() == emailnago){
                Toast.makeText(requireContext(), "NGO is already login!", Toast.LENGTH_LONG).show()

                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToNgoDashBoardFragment())
            }
            else if (auths.currentUser?.email.toString() == emaildonor){
                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToDonorDashBoardFragment())
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
          //  PrefManager.setBoolean(Constant.IS_INTRO_COMPLETE,true)
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