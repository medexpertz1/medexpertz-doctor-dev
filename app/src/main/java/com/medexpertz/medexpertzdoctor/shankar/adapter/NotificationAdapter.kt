package com.medexpertz.medexpertzdoctor.shankar.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationNewModel

class NotificationAdapter(private val context: Context, private val myCartModels: List<NotificationNewModel>, private val clickListener: ClickListener) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var notificationTitle: TextView

        init {
            notificationTitle = view.findViewById<View>(R.id.notificationTitle) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sa = myCartModels[position]

        holder.notificationTitle.text = sa.notification!!.notification
        if (sa.notification.read_status==0) holder.notificationTitle.setTextColor(context.resources.getColor(R.color.color_black))
        else holder.notificationTitle.setTextColor(context.resources.getColor(R.color.color_black_read))

        holder.notificationTitle.setOnClickListener {
            clickListener.onClick(sa.notification.notification_id!!)
        }
    }

    override fun getItemCount(): Int {
        return myCartModels.size
    }

}
