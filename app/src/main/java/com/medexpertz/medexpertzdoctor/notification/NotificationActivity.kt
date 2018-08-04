package com.medexpertz.medexpertzdoctor.notification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.medexpertz.medexpertzdoctor.NotificationActivityBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.appointment.AppointmentActivity
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Status
import javax.inject.Inject

class NotificationActivity : BaseActivity(), NotificationAdapter.OnItemClickListener {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: NotificationViewModel
    private lateinit var mBinding: NotificationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        mBinding.handler = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, mFactory)[NotificationViewModel::class.java]
        mViewModel.notifications.observe(this, Observer {
            mBinding.state = it?.status

            if (it?.status == Status.SUCCESS) {
                mBinding.adapter = NotificationAdapter(it.data!!, this)
            }
        })

        onRefresh()
    }

    fun onRefresh() {
        mViewModel.getNotifications()
    }

    override fun onNotification(notification: Notification) {
        val intent = Intent(this, AppointmentActivity::class.java)
        intent.putExtra(EXTRA_DATA, notification.appointment)
        startActivity(intent)
    }
}
