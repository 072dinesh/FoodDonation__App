package com.example.fooddonationapp.ui.tabs.ngo.recent.requsetform

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentRequestFormBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.ui.auth.login.LoginFragmentDirections
import com.example.fooddonationapp.ui.auth.registration.RegistrationFragmentDirections
import com.example.fooddonationapp.utils.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RequestFormFragment : Fragment() {

    private var _binding: FragmentRequestFormBinding? = null
    private val binding get() = _binding!!
    var types = arrayOf("Nadiad","Surat","Bharuch","Baroda","Ahmedabad")
    lateinit var typeSpin : Spinner
    private var emailngo:String?=null
    private lateinit var dbNgo : FirebaseFirestore
    private lateinit var topicList: MutableMap<String,Any>
    private lateinit var userid:String
    private lateinit var auth : FirebaseAuth
    var data = Request()
    var spinnervalue:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       _binding = FragmentRequestFormBinding.inflate(inflater,container,false)
        userid = FirebaseAuth.getInstance().currentUser!!.uid
        auth = FirebaseAuth.getInstance()

        dbNgo = FirebaseFirestore.getInstance()
        typeSpin = binding.spinner

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpin!!.setAdapter(typeAdapter)

        typeSpin.setSelection(0)

        spinner()

        binding.btnRequestFormRequest.setOnClickListener {
            Toast.makeText(requireContext(),"requested",Toast.LENGTH_LONG).show()
            requestNGO()
        }

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
                spinnervalue=types[position]
                //getString(R.string.selected_item) + " " + "" + types[position],Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    private fun requestNGO(){
        topicList = HashMap()

        var ngoname:String?=null
        dbNgo.collection("NGO").orderBy("time",Query.Direction.DESCENDING).get()
            .addOnSuccessListener {documents ->

                for (document in documents ){
                    if (document.get("email").toString()==auth.currentUser?.email.toString())
                    {
                        Timber.e(document.get("username").toString())
                        topicList["username"] = document.get("username").toString()
                        topicList["phoneno"] = document.get("phoneno").toString()
                    }
                }
                var quantity = binding.etFormQuantity.text.toString()
                var location = spinnervalue.toString()
                val a = DateTimeFormatter.ofPattern("HH:mm")
                val currenttime = LocalDateTime.now().format(a)

                val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val currentdate = LocalDateTime.now().format(dateTime)

                //val time = binding.timePicker.minute

                topicList["location"] = location

                topicList["quantity"] = quantity

                topicList["time"] = currenttime
                topicList["ngoemail"] = auth.currentUser?.email.toString()
                topicList["status"] = "Pending"
                topicList["acceptbyname"] =""
                topicList["acceptbyemail"]=""
                topicList["date"]=currentdate


                dbNgo.collection("Request")
                    .add(topicList)
                    .addOnSuccessListener {

                    }
            }



    }

}