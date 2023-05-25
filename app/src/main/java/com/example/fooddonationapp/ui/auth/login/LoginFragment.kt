package com.example.fooddonationapp.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.databinding.FragmentLoginBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.utils.PrefManager
import com.example.fooddonationapp.utils.createLoadingAlert
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

    lateinit var emailnago : String
    lateinit var emaildonor: String

    private lateinit var dbNgo : FirebaseFirestore
    private lateinit var dbDonar : FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        loadingAlert = createLoadingAlert()
        auth= FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            login()

        }


        setHasOptionsMenu(true)

        binding.signup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
        dbNgo = FirebaseFirestore.getInstance()
        dbDonar = FirebaseFirestore.getInstance()




        return binding.root
    }
    private fun login() {
        val email = binding.editEmail.text.toString()
        val password = binding.password.text.toString()


        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(), "Email and Password Can't be blank", Toast.LENGTH_LONG)
                .show()
            return

        }




        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {


            if (it.isSuccessful) {

                loadingAlert.show()
                dbDonar.collection("Donar")
                    .get()
                    .addOnSuccessListener { documents ->

                        for (document in documents )
                        {
                            // Log.e("exit", "DocumentSnapshot data: ${document.get("email")}")
                            emaildonor = document.get("email").toString()
                            Log.e("emails", "DocumentSnapshot data: ${emaildonor}")

                            if (email.equals(emaildonor)){

                                PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                                PrefManager.setString(PrefManager.ACCESS_TOKEN,"Donor")
                                var data =  PrefManager.getString(PrefManager.ACCESS_TOKEN)
                                Timber.e("Data Hsred Login",data)
                                loadingAlert.dismiss()
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToDonorDashBoardFragment()
                                )

                            }
                        }
                    }

                loadingAlert.show()
                dbNgo.collection("NGO")
                    .get()
                    .addOnSuccessListener { documents ->

                        for (document in documents )
                        {
                            emailnago = document.get("email").toString()
                            if (email.equals(emailnago)) {

                                PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                                PrefManager.setString(PrefManager.ACCESS_TOKEN, "Ngo")
                                var data =  PrefManager.getString(PrefManager.ACCESS_TOKEN)
                                Timber.e(data.toString())
                                loadingAlert.dismiss()
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNgoDashBoardFragment())



                            }
                        }

                    }

            }

        }
    }

}