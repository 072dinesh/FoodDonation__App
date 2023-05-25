package com.example.fooddonationapp.ui.tabs.ngo.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentHistoryNgoTabBinding
import com.example.fooddonationapp.databinding.FragmentRequestFormBinding
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.createLoadingAlert
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import timber.log.Timber


class HistoryNgoTabFragment : Fragment() {
    private var _binding: FragmentHistoryNgoTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var userArrayList: ArrayList<Request>
    private lateinit var myAdapter : HisoryNgoTabAdapter
    private lateinit var db :FirebaseFirestore
    private lateinit var loadingAlert: AlertDialog

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryNgoTabBinding.inflate(inflater,container,false)
        loadingAlert = createLoadingAlert()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        userArrayList = arrayListOf()

        EventChangeListerner()
        binding.recyclerview.setHasFixedSize(true)


        return binding.root
    }


    private fun EventChangeListerner(){

        loadingAlert.show()
        db.collection("Request").orderBy("date",Query.Direction.DESCENDING)
            .get().addOnSuccessListener {

                if (!it.isEmpty){


                        for(data in it.documents)
                        {

                                      val request : Request? = data.toObject(Request::class.java)
                                if (request != null){
                                    if (request.status!="Pending" && request.ngoemail == auth.currentUser?.email.toString()){
                                        userArrayList.add(request)
                                    }
                                    binding.tvDataNoteFoundHistoryNgo.isVisible = userArrayList.size == 0

                                }

                        }

                    myAdapter = HisoryNgoTabAdapter()
                    binding.recyclerview.adapter = myAdapter
                   // binding.recyclerview.adapter = HisoryNgoTabAdapter()
                    myAdapter.setData(userArrayList)
                    loadingAlert.dismiss()
                }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}