package com.medexpertz.medexpertzdoctor.appointment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medexpertz.medexpertzdoctor.AppointmentFragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity.Companion.EXTRA_DATA
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.etc.model.Status
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 14 Apr 2018 at 10:48 AM
 */
class AppointmentFragment : BaseFragment(), AppointmentAdapter.OnItemClickListener {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AppointmentViewModel
    private lateinit var mBinding: AppointmentFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mFactory)[AppointmentViewModel::class.java]
        mViewModel.appointments.observe(this, Observer {
            mBinding.state = it?.status

            if (it?.status == Status.SUCCESS) {
                it.data?.let {
                    mBinding.adapter = AppointmentAdapter(it, arguments!!.getInt(EXTRA_DATA),this)
                }
            }
        })

        if (arguments!!.getInt((EXTRA_DATA)) == 3){

        }

    }

    override fun onStart() {
        super.onStart()
        onRefresh()
    }

    fun onRefresh() {

        mViewModel.getAppointment(arguments!!.getInt(EXTRA_DATA))


    }

    override fun onAppointment(appointment: Appointment) {
        val intent = Intent(context!!, AppointmentActivity::class.java)
        intent.putExtra(EXTRA_DATA, appointment)
        startActivity(intent)
    }

    companion object {
        fun newInstance(status: Int): AppointmentFragment {
            val args = Bundle()
            args.putInt(EXTRA_DATA, status)
            val fragment = AppointmentFragment()
            fragment.arguments = args
            return fragment
        }
    }
}