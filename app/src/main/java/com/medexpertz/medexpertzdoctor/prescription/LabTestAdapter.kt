package com.medexpertz.medexpertzdoctor.prescription

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.LabTestItemBinding
import com.medexpertz.medexpertzdoctor.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 11:45 AM
 */
class LabTestAdapter(private val mItemList: ArrayList<LabTest>, private val mListener: OnItemClickListener)
    : RecyclerView.Adapter<LabTestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LabTestItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_lab_test, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    fun addItem(item: LabTest, position: Int) {
        if (position > -1) {
            mItemList[position] = item
            notifyItemChanged(position)
        } else {
            mItemList.add(item)
            notifyItemInserted(mItemList.size - 1)
        }
    }

    fun deleteItemAtPosition(position: Int): LabTest {
        val item = mItemList.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    inner class ViewHolder(private val mBinding: LabTestItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.root.setOnClickListener {
                mListener.onLabTest(mItemList[adapterPosition], adapterPosition)
            }
        }

        fun bindTo(labTest: LabTest) {
            mBinding.item = labTest
            mBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener{
        fun onLabTest(labTest: LabTest, position: Int)
    }
}