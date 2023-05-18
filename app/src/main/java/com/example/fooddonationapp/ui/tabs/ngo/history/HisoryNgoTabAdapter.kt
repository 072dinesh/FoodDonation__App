package com.example.fooddonationapp.ui.tabs.ngo.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.FragmentHistoryNgoTabBinding
import com.example.fooddonationapp.databinding.NgoItemHistoryListBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.DiffUtilExt

class HisoryNgoTabAdapter() :RecyclerView.Adapter<HisoryNgoTabAdapter.MyViewHolder>() {

    private var calllist = emptyList<Request>()
    class MyViewHolder(private val binding : NgoItemHistoryListBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(currentItem :Request){
            binding.variable = currentItem
            binding.donarName.text = itemView.context.getString(R.string.accept_by_name,currentItem.acceptbyname.toString())
            binding.NGOLocation.text = itemView.context.getString(R.string.location_ngo,currentItem.location)
            binding.NGOPhonenumber.text = itemView.context.getString(R.string.Phone_Number,currentItem.phoneno)
            binding.date.text = itemView.context.getString(R.string.date,currentItem.date)
            binding.NGOQuantity.text = itemView.context.getString(R.string.Quantity_of_foodDonated,currentItem.quantity)
            binding.status.text = itemView.context.getString(R.string.status,currentItem.status)


        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NgoItemHistoryListBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HisoryNgoTabAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HisoryNgoTabAdapter.MyViewHolder, position: Int) {
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