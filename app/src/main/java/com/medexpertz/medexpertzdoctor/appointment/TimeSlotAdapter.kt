package com.medexpertz.medexpertzdoctor.appointment

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.TimeSlotItemBinding

class TimeSlotAdapter(private val mItemList: List<TimeSlot>) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TimeSlotItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_time_slot, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(position)
    }

    fun getSelectedItem() = if (selectedPosition > -1) mItemList[selectedPosition] else null

    inner class ViewHolder(private val mBinding: TimeSlotItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.root.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun bindTo(position: Int) {
            mBinding.item = mItemList[position]
            mBinding.root.isSelected = position == selectedPosition
            mBinding.executePendingBindings()
        }
    }
}
