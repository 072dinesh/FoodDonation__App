package com.example.fooddonationapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentLoginBinding
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

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

//        setUpUi()
//        setOnClicks()
        return binding.root
    }
    private fun login()
    {
        val email = binding.editEmail.text.toString()
        val password = binding.password.text.toString()


        if (email.isBlank()||password.isBlank())
        {
            Toast.makeText(requireContext(), "Email and Password Can't be blank", Toast.LENGTH_LONG).show()
            return

        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(requireActivity()) {

            if(it.isSuccessful) {

                findNavController().navigate(

                    LoginFragmentDirections.actionLoginFragmentToDashBoardFragment()

                )

            }
            else
            {
                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_LONG).show()
            }

        }

    }


}