package com.medexpertz.medexpertzdoctor.appointment

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.medexpertz.medexpertzdoctor.etc.common.Repository
import com.medexpertz.medexpertzdoctor.etc.model.ApiService
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 18 Apr 2018 at 9:03 AM
 */
@Singleton
class AppointmentRepository @Inject constructor(private val mApi: ApiService, app: Application) : Repository(app) {
    fun getAppointment(status: Int, appointments: MutableLiveData<Resource<List<Appointment>>>) {
        appointments.value = Resource.loading()
        mApi.getAppointments(status).enqueue(object : Callback<List<Appointment>?> {
            override fun onFailure(call: Call<List<Appointment>?>?, t: Throwable?) {
                appointments.value = Resource.error()
                requestFailed(call)
                Timber.e(t)
            }

            override fun onResponse(call: Call<List<Appointment>?>?, response: Response<List<Appointment>?>) {
                appointments.value = Resource.success(response.body())
            }
        })
    }

    fun updateAppointmentStatus(appointment: Appointment, fallback: Int, updateAppointment:
    MutableLiveData<Resource<Appointment>>) {
        updateAppointment.value = Resource.loading(appointment)
        mApi.updateAppointmentStatus(appointment).enqueue(object : Callback<Any?> {
            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                appointment.appointmentStatus = fallback
                updateAppointment.value = Resource.error(data = appointment)
                requestFailed(call)
            }

            override fun onResponse(call: Call<Any?>?, response: Response<Any?>?) {
                updateAppointment.value = Resource.success(appointment)
            }
        })
    }

    fun getRejectReasons(reasons: MutableLiveData<Resource<List<Reason>>>) {
        reasons.value = Resource.loading()
        mApi.getRejectReasons().enqueue(object : Callback<List<Reason>?> {
            override fun onFailure(call: Call<List<Reason>?>?, t: Throwable?) {
                reasons.value = Resource.error()
                requestFailed(call)
            }

            override fun onResponse(call: Call<List<Reason>?>?, response: Response<List<Reason>?>) {
                reasons.value = Resource.success(response.body())
            }
        })
    }

    fun getTimeSlots(date: DateRequest, timeSlots: MutableLiveData<Resource<List<TimeSlot>>>) {
        timeSlots.value = Resource.loading()
        mApi.getTimeSlots(date).enqueue(object : Callback<List<TimeSlot>?> {
            override fun onFailure(call: Call<List<TimeSlot>?>?, t: Throwable?) {
                timeSlots.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<List<TimeSlot>?>?, response: Response<List<TimeSlot>?>) {
                if (response.code() == 200) {
                    timeSlots.value = Resource.success(response.body())
                } else {
                    timeSlots.value = Resource.success(ArrayList())
                }
            }
        })
    }
}