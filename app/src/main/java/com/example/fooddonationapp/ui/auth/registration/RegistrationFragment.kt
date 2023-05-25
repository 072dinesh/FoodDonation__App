package com.example.fooddonationapp.ui.auth.registration

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.databinding.FragmentRegistrationBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshot.Index
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    var types = arrayOf("Nadiad","Surat","Bharuch","Baroda","Ahmedabad")
    lateinit var typeSpin : Spinner

    lateinit var auth: FirebaseAuth
    private lateinit var topicList: MutableMap<String,Any>

    lateinit var name : TextView

    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReferenceDonar: DatabaseReference
    private lateinit var dbNgo : FirebaseFirestore
    private lateinit var dbDonar : FirebaseFirestore

    private val viewModel: RegistrationViewModel by viewModels()

    private var a:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)


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



        viewModel.allUsers.observe(viewLifecycleOwner, Observer {rood->


        })


        //binding.scoreText.text = viewModel.score.toString()

        binding.btncontinue.setOnClickListener {

            setOnCheckedChangeListener()


        }

        dbNgo = FirebaseFirestore.getInstance()
        dbDonar = FirebaseFirestore.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("NGO")

       databaseReferenceDonar = FirebaseDatabase.getInstance().getReference("Donar")

        typeSpin = binding.spinner

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpin!!.setAdapter(typeAdapter)

        spinner()
        return binding.root

    }

    private fun spinner(){
        binding.spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                    a=types[position]

                //getString(R.string.selected_item) + " " + "" + types[position],Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun validations():Boolean{

        var usernameInputText = binding.username.text.toString()
        val emailInputText = binding.editEmail.text.toString()
        var passwordText = binding.editPassword.text.toString()
        val phoneInputText = binding.editPhone.text.toString()
        val cityInputText = binding.spinner.selectedItemPosition
        var saas = binding.spinner.getItemAtPosition(cityInputText).toString()






        if (usernameInputText.isEmpty()){

            binding.username.setError( "This field is required")

            return false
        }
        else if (!usernameInputText.matches(".*[a-zA-Z].*".toRegex()))
        {
            binding.username.error= "Must be all alphabets "

            return false
        }

           else if (binding.spinner.selectedItem == null){
            Toast.makeText(requireContext(), "Please select city", Toast.LENGTH_SHORT).show();
        }
//          else if (binding.spinner.getSelectedItem().toString().trim().equals("Pick one")) {
//                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
//            }
        else if (emailInputText.isEmpty()){
            binding.editEmail.error = "This field is required"

            return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInputText).matches()){
            binding.editEmail.error = "Invalid Email Address"

            return false
        }
        else if(passwordText.isEmpty()) {
            binding.editPassword.setError( "This field is required")
            binding.editPassword.requestFocus()

            return false

        }
        else if(passwordText.length < 8) {
            binding.editPassword.error = "Minimum 8 Character Password"
            binding.editPassword.requestFocus()
            return false
        }
        else if(!passwordText.matches(".*[A-Z].*".toRegex())) {
            binding.editPassword.error = "Must Contain 1 Upper-case Character"
            binding.editPassword.requestFocus()
            return false
        }
        else if(!passwordText.matches(".*[a-z].*".toRegex())) {
            binding.editPassword.error = "Must Contain 1 Lower-case Character"
            binding.editPassword.requestFocus()
            return false
        }
        else if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            binding.editPassword.error = "Must Contain 1 Special Character (@#\$%^&+=)"
            binding.editPassword.requestFocus()
            return false
        }
       else if (phoneInputText.isEmpty()){
            binding.editPhone.error = "This field is required"
            binding.editPhone.requestFocus()
            return false
        }
        else if (!Patterns.PHONE.matcher(phoneInputText).matches()){
            binding.editPhone.error = "Must be all Digits"
            binding.editPhone.requestFocus()
            return false
        }
        else if(phoneInputText.length != 10){
            binding.editPhone.error = "Must be 10 Digits"
            binding.editPhone.requestFocus()
            return false
        }

        return true
    }



    private fun setOnCheckedChangeListener() {

       var Donar =  binding.materialRadioButton

        if (Donar.isChecked){
            validations()
            singUpUsers()
        }
        else
        {
            validations()
            singUpUser()
        }

    }


    private fun singUpUsers()
    {

        var email = binding.editEmail.text.toString()
        var password=binding.editPassword.text.toString()


        if (email.isBlank()||password.isBlank())
        {
            Toast.makeText(requireContext(), "Email and Password Can't be blank", Toast.LENGTH_LONG).show()
            return

        }


        var id=databaseReferenceDonar.push().key.toString()
        val add = binding.editAddress.text.toString()
        val city=a
        val phoneno = binding.editPhone.text.toString()
        val username = binding.username.text.toString()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.variable =viewModel.allUsers.value

        Timber.e(binding.variable.toString())

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()) {
                if(it.isSuccessful){

                   topicList = HashMap()

//                    topicList["id"] = id
                        topicList["add"] = add
                        topicList["email"] = email
                        topicList["password"] = password
                        topicList["city"] = city.toString()
                        topicList["phoneno"] = phoneno
                        topicList["username"]=username

                        dbDonar.collection("Donar")
                            .add(topicList)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(),"Record Inserted Successfully", Toast.LENGTH_LONG).show()

                                findNavController().navigate(

                                    RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()

                                )
                            }

//                    dbDonar.collection("Donar")
//                        .addSnapshotListener(object :EventListener<QuerySnapshot>{
//                            override fun onEvent(
//                                value: QuerySnapshot?,
//                                error: FirebaseFirestoreException?
//                            ) {
//                                if(error != null){
//
//                                }
//
//                                for (dv : DocumentChange in value?.documentChanges!!)
//                                {
//                                   if(dv.type == DocumentChange.Type.ADDED)
//                                   {
//                                       UserArray.add(dv.document.toObject(Donor::class.java))
//                                   }
//                                }
//                            }
//
//
//                        })
                    // val fooddata = Donor(id,add,email,password,city.toString(),phoneno,username)
//                    Toast.makeText(requireContext(), "Successfully Registered", Toast.LENGTH_LONG).show()
//                    var id=databaseReferenceDonar.push().key.toString()
//                    val add = binding.editAddress.text.toString()
//                    val email= binding.editEmail.text.toString()
//                    val password=binding.editPassword.text.toString()
//                    val city=a
//                    val phoneno = binding.editPhone.text.toString()
//                    val username = binding.username.text.toString()
//                    val fooddata = Donor(id,add,email,password,city.toString(),phoneno,username)
//
                    //databaseReferenceDonar.child(id).setValue(fooddata)
//                    Toast.makeText(requireContext(),"Record Inserted Successfully", Toast.LENGTH_LONG).show()
//
//                    findNavController().navigate(
//
//                        RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
//
//                    )
                }else {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_LONG).show()
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

        val add = binding.editAddress.text.toString()
        val city=a
        val phoneno = binding.editPhone.text.toString()
        val username = binding.username.text.toString()


        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()) {
                if(it.isSuccessful){

                    topicList = HashMap()

//                    topicList["id"] = id
                    topicList["add"] = add
                    topicList["email"] = email
                   // topicList["password"] = password
                    topicList["city"] = city.toString()
                    topicList["phoneno"] = phoneno
                    topicList["username"]=username


                   dbNgo.collection("NGO")
                        .add(topicList)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(),"Record Inserted Successfully", Toast.LENGTH_LONG).show()

                            findNavController().navigate(

                                RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()

                            )
                        }

                }else {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }


    }

}




