package com.example.fooddonationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fooddonationapp.databinding.ActivityMainBinding
import com.example.fooddonationapp.utils.Constant
import com.example.fooddonationapp.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //val form = RequestFormFragment()
    private lateinit var navController: NavController
    private lateinit var splashScreen: SplashScreen
    private var showSplashScreen = true
    private var emailnago: String? = null
    private var emaildonor: String? = null
    lateinit var auths: FirebaseAuth
    private lateinit var dbNgo: FirebaseFirestore
    private lateinit var dbDonar: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashScreen.setKeepOnScreenCondition {
            showSplashScreen
        }
        auths = FirebaseAuth.getInstance()
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.my_nav)
        dbNgo = FirebaseFirestore.getInstance()
        dbDonar = FirebaseFirestore.getInstance()
        val isLoggedIn = PrefManager.getBoolean(
            Constant.IS_LOGIN,
            false
        )
        val isIntroComolete = PrefManager.getBoolean(
            Constant.IS_INTRO_COMPLETE,
            false
        )
        if (isLoggedIn && isIntroComolete) {
            var data = PrefManager.getString(PrefManager.ACCESS_TOKEN)
            Timber.e(data.toString())

            if (data.toString() == "Donor") {
                //navController.navigate(R.id.donorDashBoardFragment)
                navGraph.setStartDestination(R.id.donorDashBoardFragment)
                showSplashScreen = false
            }
            if (data.toString() == "Ngo") {
                //navController.navigate(R.id.ngoDashBoardFragment)
                navGraph.setStartDestination(R.id.ngoDashBoardFragment)
                showSplashScreen = false
            }


        }

        if (!isLoggedIn) {
            navController.navigate(R.id.onBoardingFragment)
            //
        }
        if (!isIntroComolete) {
            navController.navigate(R.id.onBoardingFragment)
            //
        }


        lifecycleScope.launch {
            delay(2000)
            showSplashScreen = false

        }
        navController.graph = navGraph

    }

    private val openRequestForm: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

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