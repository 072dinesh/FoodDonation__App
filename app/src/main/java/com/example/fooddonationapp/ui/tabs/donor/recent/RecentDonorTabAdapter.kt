package com.example.fooddonationapp.ui.tabs.donor.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.ListDonorRecentBinding
import com.example.fooddonationapp.databinding.NgoItemHistoryListBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.DiffUtilExt

class RecentDonorTabAdapter(val onBtnClick : (Request)-> Unit) : RecyclerView.Adapter<RecentDonorTabAdapter.MyViewHolder>() {
    private var requestList = emptyList<Request>()
    class MyViewHolder(private val binding : ListDonorRecentBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem : Request,
        onBtnClick: (Request)-> Unit){
           binding.requestData=currentItem
            binding.tvListRecentDonorNgoName.text = itemView.context.getString(R.string.request_from_format,currentItem.ngoname)
            binding.tvListRecentDonorQuantity.text = itemView.context.getString(R.string.request_quantity_format,currentItem.quantity)
            binding.tvListRecentDonorLocation.text = itemView.context.getString(R.string.request_location_format,currentItem.location)
            binding.tvListRecentDonorNgoPhone.text = itemView.context.getString(R.string.request_phone_format,currentItem.phoneno)
            binding.button2.setOnClickListener {
                onBtnClick(currentItem)
            }
        }
        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListDonorRecentBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = requestList.getOrNull(position)
        currentItem?.let {
            holder.bind(it,onBtnClick)
        }
    }

    fun setData(Result : List<Request>){
        val diffUtil = DiffUtilExt(requestList,Result)
        val diffUtilResult  = DiffUtil.calculateDiff(diffUtil)
        requestList = Result
        diffUtilResult.dispatchUpdatesTo(this)
    }
}