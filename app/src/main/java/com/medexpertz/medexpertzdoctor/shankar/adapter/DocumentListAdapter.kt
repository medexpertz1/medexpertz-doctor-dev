package com.medexpertz.medexpertzdoctor.shankar.adapter

import android.content.Context
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.bumptech.glide.Glide
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.model.PatientDocumentFilesModel
import com.medexpertz.medexpertzdoctor.shankar.model.PatientDocumentModel

class DocumentListAdapter(private val context: Context, private val myCartModels: ArrayList<PatientDocumentModel>, private var clickListener: ClickListener) : RecyclerView.Adapter<DocumentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_document_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = myCartModels.size

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         Glide.with(context).load(myCartModels!![position].files!![0]!!.file_path).into(holder
                 .reportIV)
         holder.itemView.setOnClickListener(View.OnClickListener {
             clickListener.onClick(position)
         })
     }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val reportIV: ImageView = itemView.findViewById(R.id.reportIV)
    }
}
