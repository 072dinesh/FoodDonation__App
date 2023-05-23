package com.example.fooddonationapp.ui.tabs.ngo.recent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.databinding.FragmentRecentNgoTabBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber


class RecentNgoTabFragment : Fragment() {

    private var _binding : FragmentRecentNgoTabBinding ?= null
    private val binding get() = _binding!!
    private lateinit var requestArrayList : ArrayList<Request>
    private lateinit var recentngoAdapter : RecentNgoTabAdapter
    private lateinit var db : FirebaseFirestore
    var requestFormBrodcaster : LocalBroadcastManager ? = null
    private lateinit var topicList: MutableMap<String, Any>
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentNgoTabBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        requestArrayList = arrayListOf()
        topicList = HashMap()
        auth = FirebaseAuth.getInstance()
        recentList()
        setUpUi()
        setOnClick()
        binding.recyclerview.setHasFixedSize(true)
        return binding.root
    }
    private fun recentList(){
        recentngoAdapter = RecentNgoTabAdapter (onBtnClick = {
            db.collection("NGO").get().addOnSuccessListener {
                for (document in it){
                    if (auth.currentUser?.email.toString().equals(document.get("email"))){
                        topicList["acceptbyname"]=document.get("username").toString()
                    }
                }
            }
            it.id.let {id->
                db.collection("Request").get().addOnSuccessListener {documents->

                    for (document in documents){
                        if (id.equals(document.get("id").toString())){
                            topicList["status"]="Approve"
                            topicList["acceptedbyemail"]=auth.currentUser?.email.toString()


                            db.collection("Request").document(document.id).update(topicList)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(),"Approve",Toast.LENGTH_LONG).show()
                                }
                        }



                    }
                }
            }

        })
        db.collection("Request").get().addOnSuccessListener {
            if (!it.isEmpty){


                for (data in it.documents){

                    val request : Request? = data.toObject(Request::class.java)
                    if (request != null){

                        if (request.status == "Pending")
                        {
                            requestArrayList.add(request)
                        }

                        Timber.e("${requestArrayList.size.toString()}")
                    }
                }
                binding.recyclerview.adapter = recentngoAdapter
               recentngoAdapter.setData(requestArrayList)

            }
        }


    }


    private fun setUpUi(){
        requestFormBrodcaster = LocalBroadcastManager.getInstance(requireContext())
    }

    private fun setOnClick(){
        binding.flbAddRequest.setOnClickListener {
            Timber.e("Click","Clickckckckckkc")
           val intent = Intent(Constant.BROADCAST_RECEIVER.REQUEST_FORM)
            requestFormBrodcaster?.sendBroadcast(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}