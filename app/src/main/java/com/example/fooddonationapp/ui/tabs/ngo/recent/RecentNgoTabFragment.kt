package com.example.fooddonationapp.ui.tabs.ngo.recent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddonationapp.databinding.FragmentRecentNgoTabBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.Constant
import com.example.fooddonationapp.utils.createLoadingAlert
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
    private lateinit var loadingAlert: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentNgoTabBinding.inflate(inflater,container,false)
        loadingAlert = createLoadingAlert()
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
        recentngoAdapter = RecentNgoTabAdapter ()

        loadingAlert.show()
        db.collection("Request").get().addOnSuccessListener {

            if (!it.isEmpty){


                for (data in it.documents){

                    val request : Request? = data.toObject(Request::class.java)
                    if (request != null){

                        if (request.status == "Pending" && request.ngoemail == auth.currentUser?.email.toString())
                        {
                            requestArrayList.add(request)
                        }
                        binding.tvDataNoteFoundRecenNgo.isVisible = requestArrayList.size == 0
                        Timber.e("${requestArrayList.size.toString()}")
                    }
                }
                binding.recyclerview.adapter = recentngoAdapter
               recentngoAdapter.setData(requestArrayList)
                loadingAlert.dismiss()

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