package com.medexpertz.medexpertzdoctor.etc.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mItemList = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()

    override fun getItem(position: Int) = mItemList[position]

    override fun getCount() = mItemList.size

    override fun getPageTitle(position: Int) = mTitleList[position]

    fun addItem(item: Fragment, title: String) {
        mItemList.add(item)
        mTitleList.add(title)
        notifyDataSetChanged()
    }
}
