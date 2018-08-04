package com.medexpertz.medexpertzdoctor.appointment

import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 19 Apr 2018 at 5:38 PM
 */
class ReasonAdapter(private val mItemList: List<Reason>) : RecyclerView.Adapter<ReasonAdapter.ViewHolder>() {
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_reason, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position], position)
    }

    fun getSelectedItem(): Reason? {
        if (selectedPosition == -1) return null
        return mItemList[selectedPosition]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reasonCB = itemView as AppCompatCheckBox

        init {
            reasonCB.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(oldPosition)
                notifyItemChanged(adapterPosition)
            }
        }

        fun bindTo(reason: Reason, position: Int) {
            reasonCB.text = reason.reasonTitle
            reasonCB.isChecked = selectedPosition == position
        }
    }
}