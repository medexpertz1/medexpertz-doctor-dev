package com.medexpertz.medexpertzdoctor.prescription

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.PrescriptionItemBinding
import com.medexpertz.medexpertzdoctor.R

class PrescriptionAdapter(private val mItemList: ArrayList<Prescription>, private val mListener: OnItemClickListener) :
        RecyclerView.Adapter<PrescriptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: PrescriptionItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_prescription, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    fun addItem(prescription: Prescription, position: Int) {
        if (position > -1) {
            mItemList[position] = prescription
            notifyItemChanged(position)
        } else {
            mItemList.add(prescription)
            notifyItemInserted(mItemList.size - 1)
        }
    }

    fun deleteItemAtPosition(position: Int): Prescription {
        val item = mItemList.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    inner class ViewHolder(private val mBinding: PrescriptionItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.root.setOnClickListener {
                mListener.onPrescription(mItemList[adapterPosition], adapterPosition)
            }
        }

        fun bindTo(prescription: Prescription) {
            mBinding.item = prescription
            mBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onPrescription(prescription: Prescription, position: Int)
    }
}
