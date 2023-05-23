package com.example.fooddonationapp.ui.tabs.donor.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.databinding.FragmentRecentDonorTabBinding
import com.example.fooddonationapp.model.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import okhttp3.internal.userAgent
import timber.log.Timber


class RecentDonorTabFragment : Fragment() {
    private var _binding : FragmentRecentDonorTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestArrayList : ArrayList<Request>
    private lateinit var recentDonorAdapter : RecentDonorTabAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var topicList: MutableMap<String, Any>
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentDonorTabBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        binding.donorRecentRecycler.layoutManager = LinearLayoutManager(requireContext())
        requestArrayList = arrayListOf()
        topicList = HashMap()
        auth = FirebaseAuth.getInstance()
        recentList()
        binding.donorRecentRecycler.setHasFixedSize(true)
        return binding.root
    }
private fun recentList(){
    recentDonorAdapter = RecentDonorTabAdapter(onBtnClick = {
        db.collection("Donar").get().addOnSuccessListener {
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
                        topicList["acceptbyemail"]=auth.currentUser?.email.toString()

                        db.collection("Request").document(document.id).update(topicList)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(),"Accept",Toast.LENGTH_LONG).show()

                            }
                    }



                }
            }
        }

    })
    db.collection("Request").orderBy("dateandtime",Query.Direction.ASCENDING).get().addOnSuccessListener {
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
            binding.donorRecentRecycler.adapter = recentDonorAdapter
            recentDonorAdapter.setData(requestArrayList)

        }
    }

}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}