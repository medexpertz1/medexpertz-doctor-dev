package com.medexpertz.medexpertzdoctor.shankar.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.bumptech.glide.Glide
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.model.DoctorTimeLisrModel
import com.medexpertz.medexpertzdoctor.shankar.model.PatientDocumentModel

class DoctorTimingListAdapter(private val context: Context, private val list: ArrayList<DoctorTimeLisrModel>, private var clickListener: ClickListener) : RecyclerView.Adapter<DoctorTimingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_doctortiming_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.timingTV.setText(list[position].time)
        holder.itemView.setOnClickListener(View.OnClickListener {
            clickListener.onClick(position)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timingTV: TextView = itemView.findViewById(R.id.timingTV)
    }
}
