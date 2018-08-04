package com.medexpertz.medexpertzdoctor.notification

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.NotificationItemBinding
import com.medexpertz.medexpertzdoctor.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 03 May 2018 at 6:12 PM
 */
class NotificationAdapter(private val mItemList: List<Notification>, private val mListener: OnItemClickListener)
    : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NotificationItemBinding = DataBindingUtil.inflate(inflater, R.layout.item_notification, parent, false)
        binding.handler = mListener
        return ViewHolder(binding)
    }

    override fun getItemCount() = mItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(mItemList[position])
    }

    class ViewHolder(private val mBinding: NotificationItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindTo(notification: Notification) {
            mBinding.item = notification
            mBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onNotification(notification: Notification)
    }
}