package com.medexpertz.medexpertzdoctor.appointment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.medexpertz.medexpertzdoctor.ChangeSlotActivityBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Status
import java.util.*
import javax.inject.Inject

class ChangeSlotActivity : BaseActivity() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AppointmentViewModel
    private lateinit var mBinding: ChangeSlotActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_slot)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, mFactory)[AppointmentViewModel::class.java]
        mViewModel.setAppointment(intent)

        mBinding.appointment = mViewModel.apt
        mBinding.handler = this
        mBinding.date = DateRequest(mViewModel.apt.appointmentDate.toCalendar())

        mViewModel.timeSlots.observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                mBinding.adapter = TimeSlotAdapter(it.data!!)
            } else if (it?.status == Status.ERROR) {
                mBinding.adapter = null
            }
        })

        mViewModel.getTimeSlots(mBinding.date!!)
    }


    fun onPreviousDate(date: DateRequest) {
        date.decrement()
        mBinding.date = date
        mViewModel.getTimeSlots(date)
    }

    fun onNextDate(date: DateRequest) {
        date.increment()
        mBinding.date = date
        mViewModel.getTimeSlots(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.save) {
            val timeSlot = mBinding.adapter?.getSelectedItem()
            if (timeSlot != null) {

            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

private fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeInMillis = time
    return cal
}
