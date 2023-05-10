package com.example.fooddonationapp.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegistrationFragment : Fragment() {
    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    var types = arrayOf("Nadiad","Surat","Bharuch","Baroda","Ahmedabad")
    lateinit var typeSpin : Spinner

    lateinit var auth: FirebaseAuth

    lateinit var email: TextView
    lateinit var password: TextView
    lateinit var name : TextView
    lateinit var phoneno : TextView
    lateinit var city : TextView
    lateinit var address : TextView
    lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)
//        setUpUi()
        auth= FirebaseAuth.getInstance()

        binding.btncontinue.setOnClickListener {

            singUpUser()
        }
        auth= FirebaseAuth.getInstance()
//        binding.btnAllAccount.setOnClickListener {
////            val intent = Intent(this, loginss::class.java)
////            startActivity(intent)
////            finish()
//
//            findNavController().navigate(
//                RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
//
//            )
//        }
        databaseReference= FirebaseDatabase.getInstance().getReference("NGO")
//        setOnClicks()
//        typeSpin = binding.spinner
//
//        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        typeSpin!!.setAdapter(typeAdapter)

        typeSpin = binding.spinner

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpin!!.setAdapter(typeAdapter)

        return binding.root



    }

//    fun onDataChange(dataSnapshot: DataSnapshot) {
//        for (areaSnapshot in dataSnapshot.children) {
//            val areaName = areaSnapshot.child("areaName").getValue(
//                String::class.java
//            )
//            val areaSpinner =binding.spinner
//            val areas = arrayOf(areaName)
//            val areasAdapter = ArrayAdapter<String?>(
//                this@UAdminActivity,
//                android.R.layout.simple_spinner_item,
//                areas
//            )
//            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            areaSpinner.adapter = areasAdapter
//        }
//    }

    private fun spinner(){
        binding.spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                //getString(R.string.selected_item) + " " + "" + types[position],Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun singUpUser()
    {
        var email = binding.editEmail.text.toString()
        var password=binding.editPassword.text.toString()



        if (email.isBlank()||password.isBlank())
        {
            Toast.makeText(requireContext(), "Email and Password Can't be blank", Toast.LENGTH_LONG).show()
            return

        }
//        if (password != passconfir)
//        {
//            Toast.makeText(requireContext(), "Password and Confirm Password do not match", Toast.LENGTH_LONG).show()
//            return
//        }


        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()) {
                if(it.isSuccessful){
                    Toast.makeText(requireContext(), "Successfully Registered", Toast.LENGTH_LONG).show()
                    var id=databaseReference.push().key.toString()
                    val add = binding.editAddress.text.toString()
                    val email= binding.editEmail.text.toString()
                    val password=binding.editPassword.text.toString()
                    val city=types.first()
                    val phoneno = binding.editPhone.text.toString()
                    val username = binding.username.text.toString()
                    val fooddata = Donor(id,add,email,password,city.toString(),phoneno,username)
                    databaseReference.child(id).setValue(fooddata)
                    Toast.makeText(requireContext(),"Record Inserted Successfully", Toast.LENGTH_LONG).show()

                    findNavController().navigate(

                        RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()

                    )
                }else {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }


    }

}