package com.medexpertz.medexpertzdoctor.auth

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.SpecializationItemBinding
import com.medexpertz.medexpertzdoctor.etc.di.ClickedPosition

class SpecializationAdapter(private val mItemList: List<Specialization>, private val clickedPosition: ClickedPosition) : RecyclerView.Adapter<SpecializationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SpecializationItemBinding = DataBindingUtil.inflate(
                inflater, R.layout.item_specialization,
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    fun getSelectedSpecializations(): ArrayList<Specialization> {
        return mItemList.filter { it.isSelected } as ArrayList<Specialization>
    }

    inner class ViewHolder(private val mBinding: SpecializationItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.root.setOnClickListener {
                mItemList[adapterPosition].invertSelection()
                notifyItemChanged(adapterPosition)
                clickedPosition.onClicked(adapterPosition)
            }
        }

        fun bindTo(item: Specialization) {
            mBinding.item = item
            mBinding.executePendingBindings()
        }
    }
}
