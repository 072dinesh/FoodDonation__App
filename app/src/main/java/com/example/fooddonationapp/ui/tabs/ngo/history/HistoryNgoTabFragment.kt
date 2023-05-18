package com.example.fooddonationapp.ui.tabs.ngo.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentHistoryNgoTabBinding
import com.example.fooddonationapp.databinding.FragmentRequestFormBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.model.Request
import com.google.firebase.firestore.*
import timber.log.Timber


class HistoryNgoTabFragment : Fragment() {
    private var _binding: FragmentHistoryNgoTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var userArrayList: ArrayList<Request>
    private lateinit var myAdapter : HisoryNgoTabAdapter
    private lateinit var db :FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryNgoTabBinding.inflate(inflater,container,false)

        db = FirebaseFirestore.getInstance()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        userArrayList = arrayListOf()

        EventChangeListerner()
        binding.recyclerview.setHasFixedSize(true)


        return binding.root
    }
    private fun EventChangeListerner(){
        db.collection("Request").orderBy("time",Query.Direction.DESCENDING)
            .get().addOnSuccessListener {

                if (!it.isEmpty){

                        for(data in it.documents)
                        {
                            val request : Request? = data.toObject(Request::class.java)
                            if (request != null){
                                userArrayList.add(request)
                            }
                        }
                    myAdapter = HisoryNgoTabAdapter()
                    binding.recyclerview.adapter = myAdapter
                   // binding.recyclerview.adapter = HisoryNgoTabAdapter()
                    myAdapter.setData(userArrayList)

                }


        }
//            .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                override fun onEvent(
//                    value: QuerySnapshot?,
//                    error: FirebaseFirestoreException?
//                ) {
//                    if(error != null){
//                            Timber.e(error.message)
//                    }
//                    for (dv : DocumentChange in value?.documentChanges!!)
//                    {
//                        if(dv.type == DocumentChange.Type.ADDED)
//                        {
//                            var a = dv.document.toObject(Request::class.java)
//                             userArrayList.add(a)
//
//                        }
//                    }
//                    myAdapter = HisoryNgoTabAdapter()
//                    binding.recyclerview.adapter = myAdapter
//                    myAdapter.setData(userArrayList)
//
//                }
//            })

    }




}