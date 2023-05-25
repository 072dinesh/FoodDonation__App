package com.example.fooddonationapp.ui.tabs.donor.recent

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.ListDonorRecentBinding
import com.example.fooddonationapp.databinding.NgoItemHistoryListBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.DiffUtilExt
import com.google.common.io.Files.append

class RecentDonorTabAdapter(val onBtnClick : (Request)-> Unit) : RecyclerView.Adapter<RecentDonorTabAdapter.MyViewHolder>() {
    private var requestList = emptyList<Request>()
    class MyViewHolder(private val binding : ListDonorRecentBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem : Request,
        onBtnClick: (Request)-> Unit){
           binding.requestData=currentItem
            binding.tvListRecentDonorNgoName.text =
            SpannableStringBuilder().append(itemView.context.getString(R.string.request_from_format)).bold { append(currentItem.ngoname)}

            binding.tvListRecentDonorQuantity.text =
            SpannableStringBuilder().append(itemView.context.getString(R.string.request_quantity_format)).bold { append(currentItem.quantity).append(itemView.context.getString(R.string.people))}
            binding.tvListRecentDonorLocation.text =
            SpannableStringBuilder().append(itemView.context.getString(R.string.request_location_format)).bold { append(currentItem.location)}
            binding.tvListRecentDonorNgoPhone.text =
            SpannableStringBuilder().append(itemView.context.getString(R.string.request_phone_format)).bold { append(currentItem.phoneno)}
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

    fun removeItem(currentItem: Request) {
        val index = requestList.indexOf(currentItem)
        if(index != -1){
            val newList = requestList.toMutableList()
            newList.remove(currentItem)
            setData(newList)
        }
    }

    fun setData(Result : List<Request>){
        val diffUtil = DiffUtilExt(requestList,Result)
        val diffUtilResult  = DiffUtil.calculateDiff(diffUtil)
        requestList = Result
        diffUtilResult.dispatchUpdatesTo(this)
    }
}