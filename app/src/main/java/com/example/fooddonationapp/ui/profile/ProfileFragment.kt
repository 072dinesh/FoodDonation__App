package com.example.fooddonationapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.databinding.FragmentProfileBinding
import com.example.fooddonationapp.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    lateinit var password: TextView
    lateinit var name: TextView
    lateinit var phoneno: TextView
    lateinit var city: TextView
    lateinit var address: TextView
    private lateinit var topicList: MutableMap<String, Any>

    // lateinit var databaseReference: DatabaseReference
    // lateinit var databaseReferenceDonar: DatabaseReference
    private lateinit var db: FirebaseFirestore

    // private lateinit var dbDonor : FirebaseFirestore
    private var a: String? = null
    lateinit var typeSpin: Spinner
    var data = PrefManager.getString(PrefManager.ACCESS_TOKEN)
    var types = arrayOf("Nadiad", "Surat", "Bharuch", "Baroda", "Ahmedabad")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()
        //dbDonar = FirebaseFirestore.getInstance()
        typeSpin = binding.spinner

        val typeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpin!!.setAdapter(typeAdapter)
        spinner()
        updateUser()
        setOnClicks()
        return binding.root
    }


    private fun spinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                a = types[position]

                //textView.text = text
//                textView.text = text
                //getString(R.string.selected_item) + " " + "" + types[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun updateUser() {

        Timber.e(data.toString())
        var email = auth.currentUser?.email.toString()
        if (data.toString() == "Donor") {
            //navController.navigate(R.id.donorDashBoardFragment)
            db.collection("Donar").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    var donoremail = document.get("email").toString()
                    if (email.equals(donoremail)) {
                        Timber.e(document.get("username").toString())
                        binding.editAdd.setText(document.get("add").toString())
                        binding.username.setText(document.get("username").toString())
                        binding.editPassword.setText(document.get("email").toString())
                        binding.editPhoneno.setText(document.get("phoneno").toString())
                        for (city in types) {
                            Timber.e(city.toString())
                            Timber.e(document.get("city").toString())
                            if (city.equals(document.get("city").toString())) {
                                binding.spinner.setSelection(types.indexOf(city))
                                Timber.e(types.indexOf(city).toString())
                            }
                        }

                        getAndSetData()
                        binding.btncontinue.setOnClickListener {
                            getAndSetData()
                            db.collection("Donar").document(document.id).update(topicList)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Update Success",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Not Updated ",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }


                    }
                }

            }

        }

        if (data.toString() == "Ngo") {

            //navController.navigate(R.id.ngoDashBoardFragment)
            db.collection("NGO").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    var donoremail = document.get("email").toString()
                    if (email.equals(donoremail)) {
                        Timber.e(document.get("username").toString())
                        binding.editAdd.setText(document.get("add").toString())
                        binding.username.setText(document.get("username").toString())
                        binding.editPassword.setText(document.get("email").toString())
                        binding.editPhoneno.setText(document.get("phoneno").toString())
                        for (city in types) {
                            Timber.e(city.toString())
                            Timber.e(document.get("city").toString())
                            if (city.equals(document.get("city").toString())) {
                                binding.spinner.setSelection(types.indexOf(city))
                                Timber.e(types.indexOf(city).toString())
                            }
                        }

                            getAndSetData()
                        binding.btncontinue.setOnClickListener {
                            getAndSetData()
                            db.collection("NGO").document(document.id).update(topicList)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Update Success",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Not Updated ",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }

                }

            }
            // var id=databaseReferenceDonar.push().key.toString()


        }

    }

    fun setOnClicks() {
        binding.imgback.setOnClickListener {
            findNavController().navigateUp()
        }

    }
    private fun getAndSetData(){
        val add = binding.editAdd.text.toString()
        val city = a
        val username = binding.username.text.toString()
        val phone = binding.editPhoneno.text.toString()
        val emaill = binding.editPassword.text.toString()
        topicList = HashMap()

//                    topicList["id"] = id
        topicList["add"] = add
        topicList["email"] = emaill
        //topicList["password"] = password
        topicList["city"] = city.toString()
        topicList["phoneno"] = phone
        topicList["username"] = username.toString()
    }
}