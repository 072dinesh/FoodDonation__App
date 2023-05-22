package com.example.fooddonationapp.ui.tabs.donor.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.databinding.FragmentRecentDonorTabBinding
import com.example.fooddonationapp.model.Request
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.internal.userAgent
import timber.log.Timber


class RecentDonorTabFragment : Fragment() {
    private var _binding : FragmentRecentDonorTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestArrayList : ArrayList<Request>
    private lateinit var recentDonorAdapter : RecentDonorTabAdapter
    private lateinit var db : FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecentDonorTabBinding.inflate(inflater,container,false)
        db = FirebaseFirestore.getInstance()
        binding.donorRecentRecycler.layoutManager = LinearLayoutManager(requireContext())
        requestArrayList = arrayListOf()


        recentList()
        binding.donorRecentRecycler.setHasFixedSize(true)
        return binding.root
    }
private fun recentList(){
    db.collection("Request").get().addOnSuccessListener {
        if (!it.isEmpty){
            for (data in it.documents){

                val request : Request? = data.toObject(Request::class.java)
                if (request != null){

                    requestArrayList.add(request)
                    Timber.e("${requestArrayList.size.toString()}")
                }
            }
            recentDonorAdapter = RecentDonorTabAdapter()
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