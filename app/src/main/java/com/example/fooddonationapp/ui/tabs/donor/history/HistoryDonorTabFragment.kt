package com.example.fooddonationapp.ui.tabs.donor.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentHistoryDonorTabBinding
import com.example.fooddonationapp.databinding.FragmentRequestFormBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.ui.tabs.ngo.history.HisoryNgoTabAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HistoryDonorTabFragment : Fragment() {
    private var _binding: FragmentHistoryDonorTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var userArrayList: ArrayList<Request>
    private lateinit var myAdapter : HistoryDonorTabAdapter
    private lateinit var db : FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryDonorTabBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        userArrayList = arrayListOf()
        auth= FirebaseAuth.getInstance()
        EventChangeListerner()
        binding.recyclerview.setHasFixedSize(true)

       return binding.root
    }
    fun EventChangeListerner()
    {
        db.collection("Request").orderBy("date", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {

                if (!it.isEmpty){

                    for(data in it.documents)
                    {
                        if (data.get("acceptbyemail").toString()==auth.currentUser?.email.toString()){

                            val request : Request? = data.toObject(Request::class.java)
                            if (request != null){
                                userArrayList.add(request)
                            }
                        }

                    }
                    myAdapter = HistoryDonorTabAdapter()
                    binding.recyclerview.adapter = myAdapter
                    // binding.recyclerview.adapter = HisoryNgoTabAdapter()
                    myAdapter.setData(userArrayList)

                }


            }
    }

}