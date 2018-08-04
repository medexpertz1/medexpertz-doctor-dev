package com.medexpertz.medexpertzdoctor.appointment

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.medexpertz.medexpertzdoctor.AppointmentItemBinding
import com.medexpertz.medexpertzdoctor.R

class AppointmentAdapter(private val mItemList: List<Appointment>, private val num: Int, private val mListener: OnItemClickListener)
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: AppointmentItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_appointment, parent, false)
        binding.handler = mListener
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
        if (num == 3){
            holder.callIV.visibility = View.VISIBLE
        }
        else{
            holder.callIV.visibility = View.GONE
        }
    }

    class ViewHolder(private val mBinding: AppointmentItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindTo(appointment: Appointment) {
            mBinding.item = appointment
            mBinding.executePendingBindings()
        }
        var callIV: ImageView = itemView.findViewById(R.id.callIV)
    }

    interface OnItemClickListener {
        fun onAppointment(appointment: Appointment)
    }
}
