package com.example.fooddonationapp.ui.tabs.ngo.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddonationapp.R
import com.example.fooddonationapp.databinding.ListNgoRecentBinding
import com.example.fooddonationapp.model.Request
import com.example.fooddonationapp.utils.DiffUtilExt

class RecentNgoTabAdapter(val onBtnClick : (Request)-> Unit) :RecyclerView.Adapter<RecentNgoTabAdapter.MyViewHolder>(){
    private var requestList = emptyList<Request>()
    class MyViewHolder(private val binding : ListNgoRecentBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem :Request,onBtnClick: (Request)-> Unit){
            binding.data=currentItem
            binding.tvListRecentNgoQuantity.text=itemView.context.getString(R.string.quanitiy_of_food_donated,currentItem.quantity.toString())
            binding.tvListRecentNgoLocation.text=itemView.context.getString(R.string.location_recent_ngo,currentItem.location.toString())
            binding.tvListRecentNgoDate.text=itemView.context.getString(R.string.food_donated_date,currentItem.date.toString())
            binding.tvListRecentNgoTime.text=itemView.context.getString(R.string.Time,currentItem.time.toString())
//            binding.button2.setOnClickListener {
//                onBtnClick(currentItem)
//            }
        }
        companion object {
            fun from(parent: ViewGroup): RecentNgoTabAdapter.MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListNgoRecentBinding.inflate(layoutInflater, parent, false)
                return RecentNgoTabAdapter.MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return RecentNgoTabAdapter.MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return  requestList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = requestList.getOrNull(position)
        currentItem?.let {
            holder.bind(it,onBtnClick)
        }
    }
    fun setData(Result:List<Request>){

 val userDiffUtil = DiffUtilExt(requestList, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        requestList = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }
}