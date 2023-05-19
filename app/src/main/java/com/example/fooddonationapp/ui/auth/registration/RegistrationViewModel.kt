package com.example.fooddonationapp.ui.auth.registration

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddonationapp.R
import com.example.fooddonationapp.model.Donor
import com.example.fooddonationapp.utils.BaseViewModel
import com.example.fooddonationapp.utils.NetworkResult
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {
    private lateinit var dbNgo : FirebaseFirestore
    private lateinit var dbDonar : FirebaseFirestore

    private val _allUsers = MutableLiveData<Donor>()
    val allUsers: LiveData<Donor>
        get() = _allUsers





init {
  _allUsers.value
}


}