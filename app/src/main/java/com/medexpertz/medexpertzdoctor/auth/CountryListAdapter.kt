package com.medexpertz.medexpertzdoctor.auth

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.CountryItemBinding
import com.medexpertz.medexpertzdoctor.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 06 Apr 2018 at 1:18 PM
 */
class CountryListAdapter(private val mItemList: List<Country>, private val mListener: OnCountryItemClick)
    : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CountryItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_country, parent, false)
        binding.listener = mListener
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    class ViewHolder(private val mBinding: CountryItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindTo(country: Country) {
            mBinding.country = country
            mBinding.executePendingBindings()
        }
    }

    interface OnCountryItemClick {
        fun onCountry(country: Country)
    }
}