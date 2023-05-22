package com.example.fooddonationapp.ui.tabs.donor.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.DonorItemHistoryListBinding
import com.example.fooddonationapp.databinding.NgoItemHistoryListBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.DiffUtilExt



class HistoryDonorTabAdapter() : RecyclerView.Adapter<HistoryDonorTabAdapter.MyViewHolder>() {

    private var calllist = emptyList<Request>()
    class MyViewHolder(private val binding : DonorItemHistoryListBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(currentItem : Request){

            binding.variable = currentItem
            binding.ngoName.text = itemView.context.getString(R.string.ngo_name,currentItem.ngoname.toString())
            binding.NGOLocation.text = itemView.context.getString(R.string.location_ngo,currentItem.location)
            binding.NGOPhonenumber.text = itemView.context.getString(R.string.ngo_phone_number,currentItem.phoneno)
            binding.date.text = itemView.context.getString(R.string.donation_accepted_date,currentItem.date)
            binding.NGOQuantity.text = itemView.context.getString(R.string.Donated_food_quantity,currentItem.quantity)



        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DonorItemHistoryListBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryDonorTabAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HistoryDonorTabAdapter.MyViewHolder, position: Int) {
        val currentItem = calllist.getOrNull(position)

        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return  calllist.size
    }

    fun setData(Result:List<Request>){

//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(calllist, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        calllist = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}