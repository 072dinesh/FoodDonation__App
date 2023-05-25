package com.example.fooddonationapp.ui.tabs.donor.recent

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.databinding.FragmentRecentDonorTabBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.createLoadingAlert
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import okhttp3.internal.userAgent
import timber.log.Timber
import kotlin.concurrent.fixedRateTimer


class RecentDonorTabFragment : Fragment() {
    private var _binding : FragmentRecentDonorTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestArrayList : ArrayList<Request>
    private lateinit var recentDonorAdapter : RecentDonorTabAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var topicList: MutableMap<String, Any>
    lateinit var auth: FirebaseAuth
    private lateinit var loadingAlert: androidx.appcompat.app.AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentDonorTabBinding.inflate(inflater,container,false)
        loadingAlert = createLoadingAlert()
        db = FirebaseFirestore.getInstance()
        binding.donorRecentRecycler.layoutManager = LinearLayoutManager(requireContext())
        requestArrayList = arrayListOf()
        topicList = HashMap()
        auth = FirebaseAuth.getInstance()
        recentList()
        binding.donorRecentRecycler.setHasFixedSize(true)
       fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        return binding.root

    }

private fun recentList(){
    db.collection("Donar").get().addOnSuccessListener {
        for (document in it){
            if (auth.currentUser?.email.toString().equals(document.get("email"))){
                topicList["acceptbyname"]=document.get("username").toString()
            }
        }
    }
    recentDonorAdapter = RecentDonorTabAdapter(onBtnClick = {

        it.id.let {id->

            db.collection("Request").get().addOnSuccessListener {documents->

                        for (document in documents){
                            if (id.equals(document.get("id").toString())){
                                topicList["status"]="Approve"
                                topicList["acceptedbyemail"]=auth.currentUser?.email.toString()


                                db.collection("Request").document(document.id).update(topicList)
                                    .addOnSuccessListener {
                                        Toast.makeText(requireContext(),"Accepted",Toast.LENGTH_LONG).show()
                                    }
                            }



                }
            }
            recentDonorAdapter.removeItem(it)
//            if (requestArrayList.size == 0){
//                binding.textView4.isVisible = true
//            }

        }


    })
    loadingAlert.show()
    db.collection("Request").orderBy("dateandtime", Query.Direction.ASCENDING).get().addOnSuccessListener {
        if (!it.isEmpty){


            for (data in it.documents){

                val request : Request? = data.toObject(Request::class.java)
                if (request != null){

                    if (request.status == "Pending")
                    {
                        requestArrayList.add(request)
                    }else{
                    }

                    binding.textView4.isVisible = requestArrayList.size == 0


                    Timber.e(requestArrayList.size.toString())
                }
            }

            binding.donorRecentRecycler.adapter = recentDonorAdapter
            recentDonorAdapter.setData(requestArrayList)
            loadingAlert.dismiss()

        }
    }

}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}