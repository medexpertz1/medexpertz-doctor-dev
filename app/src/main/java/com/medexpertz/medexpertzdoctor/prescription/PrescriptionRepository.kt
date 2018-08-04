package com.medexpertz.medexpertzdoctor.prescription

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.medexpertz.medexpertzdoctor.appointment.Appointment
import com.medexpertz.medexpertzdoctor.etc.common.Repository
import com.medexpertz.medexpertzdoctor.etc.model.ApiService
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Apr 2018 at 10:33 AM
 */
@Singleton
class PrescriptionRepository @Inject constructor(app: Application, private val mApi: ApiService) : Repository(app) {

    fun getMedicine(search: String, medicines: MutableLiveData<Resource<List<Medicine>>>) {
        medicines.value = Resource.loading()
        mApi.getMedicines(MedicineSearch(search)).enqueue(object : Callback<List<Medicine>?> {
            override fun onFailure(call: Call<List<Medicine>?>?, t: Throwable?) {
                medicines.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<List<Medicine>?>?, response: Response<List<Medicine>?>) {
                when (response.code()) {
                    200 -> medicines.value = Resource.success(response.body())
                    204 -> medicines.value = Resource.success(ArrayList())
                    else -> throw IllegalStateException("Invalid response code ${response.code()}")
                }
            }
        })
    }

    fun addPrescription(prescription: Prescription, updatePrescription: MutableLiveData<Resource<Prescription>>) {
        updatePrescription.value = Resource.loading()
        mApi.updatePrescription(prescription).enqueue(object : Callback<Prescription?> {
            override fun onFailure(call: Call<Prescription?>?, t: Throwable?) {
                updatePrescription.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<Prescription?>?, response: Response<Prescription?>) {
                updatePrescription.value = Resource.success(response.body())
            }
        })
    }

    fun getPrescriptions(appointment: Appointment, prescriptions: MutableLiveData<Resource<Prescriptions>>) {
        prescriptions.value = Resource.loading()
        mApi.getPrescription(appointment.orderAppointmentId).enqueue(object : Callback<Prescriptions?> {
            override fun onFailure(call: Call<Prescriptions?>?, t: Throwable?) {
                prescriptions.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<Prescriptions?>?, response: Response<Prescriptions?>) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()!!
                        body.medicines?.forEach { it.orderAppointmentId = appointment.orderAppointmentId }
                        body.labtests?.forEach { it.orderAppointmentId = appointment.orderAppointmentId }
                        body.summary?.forEach { it.orderAppointmentId = appointment.orderAppointmentId }
                        prescriptions.value = Resource.success(body)
                    }
                    else -> prescriptions.value = Resource.success(Prescriptions(ArrayList(), ArrayList(), ArrayList()))
                }
            }
        })
    }

    fun addLabTest(labTest: LabTest, updateLabTest: MutableLiveData<Resource<LabTest>>) {
        updateLabTest.value = Resource.loading()
        mApi.updateLabTest(labTest).enqueue(object : Callback<LabTest?> {
            override fun onFailure(call: Call<LabTest?>?, t: Throwable?) {
                updateLabTest.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<LabTest?>?, response: Response<LabTest?>) {
                updateLabTest.value = Resource.success(response.body())
            }
        })
    }

    fun getLabTest(search: String, appointment: Appointment, labTests: MutableLiveData<Resource<List<LabTest>>>) {
        labTests.value = Resource.loading()
        mApi.getLabTests(LabTestSearch(search)).enqueue(object : Callback<List<LabTest>?> {
            override fun onFailure(call: Call<List<LabTest>?>?, t: Throwable?) {
                labTests.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<List<LabTest>?>?, response: Response<List<LabTest>?>) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()!!
                        body.forEach { it.orderAppointmentId = appointment.orderAppointmentId }
                        labTests.value = Resource.success(body)
                    }
                    else -> labTests.value = Resource.success(ArrayList())
                }
            }
        })
    }

    fun updateSummary(summary: Summary, updateSummary: MutableLiveData<Resource<Summary>>) {
        updateSummary.value = Resource.loading()
        mApi.updateSummary(summary).enqueue(object : Callback<Summary?> {
            override fun onFailure(call: Call<Summary?>?, t: Throwable?) {
                requestFailed(call, t)
                updateSummary.value = Resource.error()
            }

            override fun onResponse(call: Call<Summary?>?, response: Response<Summary?>) {
                updateSummary.value = Resource.success(response.body())
            }
        })
    }

    fun deletePrescription(prescription: Prescription, updatePrescription: MutableLiveData<Resource<Prescription>>) {
        updatePrescription.value = Resource.loading()
        mApi.deletePrescription(prescription).enqueue(object: Callback<Any?> {
            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                updatePrescription.value = Resource.error(data = prescription)
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<Any?>?, response: Response<Any?>?) {
                updatePrescription.value = Resource.success(prescription)
            }
        })
    }

    fun deleteLabTest(labTest: LabTest, deleteLabTest: MutableLiveData<Resource<LabTest>>) {
        deleteLabTest.value = Resource.loading()
        mApi.deleteLabTest(labTest).enqueue(object: Callback<Any?> {
            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                deleteLabTest.value = Resource.error(data = labTest)
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<Any?>?, response: Response<Any?>?) {
                deleteLabTest.value = Resource.success(labTest)
            }
        })
    }
}