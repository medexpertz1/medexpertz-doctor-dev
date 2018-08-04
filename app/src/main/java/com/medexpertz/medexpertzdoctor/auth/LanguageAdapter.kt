package com.medexpertz.medexpertzdoctor.auth

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.LanguageItemBinding
import com.medexpertz.medexpertzdoctor.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 17 Apr 2018 at 7:50 PM
 */
class LanguageAdapter(private val mItemList: List<Language>) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LanguageItemBinding = DataBindingUtil.inflate(
                inflater, R.layout.item_language,
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    fun getSelectedLanguages(): ArrayList<Language> {
        return mItemList.filter { it.isSelected } as ArrayList<Language>
    }

    inner class ViewHolder(private val mBinding: LanguageItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.root.setOnClickListener {
                mItemList[adapterPosition].invertSelection()
                notifyItemChanged(adapterPosition)
            }
        }

        fun bindTo(item: Language) {
            mBinding.item = item
            mBinding.executePendingBindings()
        }
    }
}