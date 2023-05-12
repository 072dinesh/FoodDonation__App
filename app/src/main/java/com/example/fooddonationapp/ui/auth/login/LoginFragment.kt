package com.example.fooddonationapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.databinding.FragmentLoginBinding
import com.example.fooddonationapp.model.Donor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var emailnago : String
    lateinit var emaildonor: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        auth= FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            login()
        }

        setHasOptionsMenu(true)

        binding.signup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("NGO")
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Donor")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    var userdata = data.getValue(Donor::class.java)
                    emailnago = userdata?.email.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        databaseReference2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    var userdata = data.getValue(Donor::class.java)
                    emaildonor = userdata?.email.toString()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//        setUpUi()
//        setOnClicks()
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
//
//        private fun CheckUserLogin(){
//            if (auth.currentUser != null) {
//
//                Toast.makeText(requireContext(), "user is already login!", Toast.LENGTH_LONG).show()
//
//                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToDashBoardFragment())
//
//
//            } else {
//
//
//                setUpUi()
//                setOnClicks()
//
//            }
//        }


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {


            if (it.isSuccessful) {

                    if (email.equals(emailnago)){
                        findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToNgoDashBoardFragment()
                )
                    }
              else {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToDonorDashBoardFragment()
                        )
                    }

//                if(email.equals(auth.currentUser?.)){
//                    LoginFragmentDirections.actionLoginFragmentToDonorDashBoardFragment()
//                }
//
//            }
//            else if (it.isSuccessful){
//                findNavController().navigate(
//                LoginFragmentDirections.actionLoginFragmentToDonorDashBoardFragment()
//                )
//            }
//            else
//            {
//                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_LONG).show()
//            }

            }

        }
    }

}