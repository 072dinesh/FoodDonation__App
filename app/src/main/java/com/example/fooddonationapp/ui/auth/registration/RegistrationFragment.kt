package com.example.fooddonationapp.ui.auth.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentOnBoardingBinding
import com.example.fooddonationapp.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {
    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    var types = arrayOf("Nadiad","Surat","Bharuch","Baroda","Ahmedabad")
    lateinit var typeSpin : Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)
//        setUpUi()

//        setOnClicks()
        typeSpin = binding.spinner

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpin!!.setAdapter(typeAdapter)


//        binding.spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//
//                var v = binding.editText.text.toString().toDoubleOrNull()
//                viewmodeltmp.selectedUnit = types[position].first().uppercase()
//                viewmodeltmp.calculate(v?:0.0)
//                //binding.tmpshow = viewmodeltmp
//                Toast.makeText(applicationContext,"${types[position]}", Toast.LENGTH_SHORT).show()
//
//                //getString(R.string.selected_item) + " " + "" + types[position],Toast.LENGTH_SHORT).show()
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
        return binding.root

    }


}