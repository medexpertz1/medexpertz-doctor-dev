package com.medexpertz.medexpertzdoctor.prescription

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.SummaryFragmentBinding
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.etc.model.Status
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 11:01 AM
 */
class SummaryFragment: BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: PrescriptionViewModel
    private lateinit var mBinding: SummaryFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        mBinding.handler = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!, mFactory)[PrescriptionViewModel::class.java]
        mViewModel.getAppointmentDetails().observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                if (it.data!!.summary!!.isEmpty()) {
                    mBinding.summary = Summary(orderAppointmentId = mViewModel.appointment.orderAppointmentId)
                } else {
                    mBinding.summary = it.data.summary!![0]
                    mBinding.summary!!.orderAppointmentId = mViewModel.appointment.orderAppointmentId
                }
            }
        })
    }

    fun onSummary(summary: Summary) {
        mViewModel.updateSummary(summary).observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                Toast.makeText(context, R.string.summary_updated, Toast.LENGTH_SHORT).show()
                mViewModel.updateState(PrescriptionState.FINISHED)
            }
        })
    }
}