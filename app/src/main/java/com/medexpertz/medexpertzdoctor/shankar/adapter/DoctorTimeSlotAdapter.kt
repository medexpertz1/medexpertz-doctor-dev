package com.medexpertz.medexpertzdoctor.shankar.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.model.DoctorTimeValueModel

class DoctorTimeSlotAdapter (private val context: Context, private val list: ArrayList<DoctorTimeValueModel>, private var clickListener: ClickListener) : RecyclerView.Adapter<DoctorTimeSlotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.timeslot_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.timeTV.setText(list[position].time)
        holder.itemView.setOnClickListener(View.OnClickListener {
            clickListener.onClick(position)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTV: TextView = itemView.findViewById(R.id.timeTV)
    }
}
