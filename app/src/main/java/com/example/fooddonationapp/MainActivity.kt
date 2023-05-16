package com.example.fooddonationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fooddonationapp.databinding.ActivityMainBinding
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.ui.tabs.ngo.recent.requsetform.RequestFormFragment
import com.example.fooddonationapp.utils.Constant

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding!!
    //val form = RequestFormFragment()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
       installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController

    }
    private val openRequestForm : BroadcastReceiver =
        object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                //binding.fragmentContainerView.getFragment<RequestFormFragment>()
//               // fragmentManager.findFragmentById(R.id.requestFormFragment)
//                supportFragmentManager.addOnBackStackChangedListener {
//                    binding.fragmentContainerView.cur
                //supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragmentContainerView, form).addToBackStack(null)
//                    .commit()
//                }
                navController.navigate(R.id.requestFormFragment)




            }

        }


    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            (openRequestForm),
            IntentFilter(Constant.BROADCAST_RECEIVER.REQUEST_FORM)
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(openRequestForm)
    }
}