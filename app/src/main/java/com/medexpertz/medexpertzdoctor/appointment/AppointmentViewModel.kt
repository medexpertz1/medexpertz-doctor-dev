package com.medexpertz.medexpertzdoctor.appointment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity.Companion.EXTRA_DATA
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(private val mRepo: AppointmentRepository) : ViewModel() {
    lateinit var apt: Appointment

    val appointments = MutableLiveData<Resource<List<Appointment>>>()
    val appointment = MutableLiveData<Resource<Appointment>>()
    private val reasons = MutableLiveData<Resource<List<Reason>>>()
    val timeSlots = MutableLiveData<Resource<List<TimeSlot>>>()

    fun getAppointment(status: Int) {
        mRepo.getAppointment(status, appointments)
    }

    fun updateAppointmentStatus(status: Int = appointment.value?.data?.appointmentStatus!! + 1,
                                fallback: Int = appointment.value?.data?.appointmentStatus!!): LiveData<Resource<Appointment>> {
        val request = appointment.value?.data!!
        request.appointmentStatus = status
        mRepo.updateAppointmentStatus(request, fallback, appointment)
        return appointment
    }

    fun initAppointment(intent: Intent) {
        appointment.value = Resource.success(intent.getSerializableExtra(BaseActivity.EXTRA_DATA) as Appointment)
    }

    fun getReasons(): LiveData<Resource<List<Reason>>> {
        mRepo.getRejectReasons(reasons)
        return reasons
    }

    fun rejectAppointmentStatus(reason: Reason): LiveData<Resource<Appointment>> {
        appointment.value?.data!!.reasonTitle = reason.reasonTitle
        updateAppointmentStatus(5)
        return appointment
    }

    fun setAppointment(intent: Intent) {
        apt = intent.getSerializableExtra(EXTRA_DATA) as Appointment
    }

    fun getTimeSlots(date: DateRequest) {
        mRepo.getTimeSlots(date, timeSlots)
    }
}
