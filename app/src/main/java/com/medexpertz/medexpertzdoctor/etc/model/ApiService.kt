package com.medexpertz.medexpertzdoctor.etc.model

import com.medexpertz.medexpertzdoctor.appointment.Appointment
import com.medexpertz.medexpertzdoctor.appointment.DateRequest
import com.medexpertz.medexpertzdoctor.appointment.Reason
import com.medexpertz.medexpertzdoctor.appointment.TimeSlot
import com.medexpertz.medexpertzdoctor.auth.*
import com.medexpertz.medexpertzdoctor.notification.Notification
import com.medexpertz.medexpertzdoctor.prescription.*
import com.medexpertz.medexpertzdoctor.profile.Doctor
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Nov 2017 at 2:48 PM
 */
interface ApiService {
    @Headers(HEADER_NO_AUTH)
    @POST("doctor-login")
    fun signIn(@Body auth: Auth): Call<ProfileResponse>

    @Headers(HEADER_NO_AUTH)
    @GET("get-specialization")
    fun getSpecializations(): Call<List<Specialization>>

    @Headers(HEADER_NO_AUTH)
    @GET("get-country-list")
    fun getCountryList(): Call<List<Country>>

    @Headers(HEADER_NO_AUTH)
    @GET("get-languages")
    fun getLanguages(): Call<List<Language>>

    @Headers(HEADER_NO_AUTH)
    @POST("doctor-register")
    fun register(@Body profile: Profile): Call<Token>

    @GET("get-appointments-by-status/{status}")
    fun getAppointments(@Path("status") status: Int): Call<List<Appointment>>

    @POST("change-order-appointment-status")
    fun updateAppointmentStatus(@Body appointment: Appointment): Call<Any>

    @GET("appointment-cancel-status")
    fun getRejectReasons(): Call<List<Reason>>

    @POST("get-medicine-list")
    fun getMedicines(@Body search: MedicineSearch): Call<List<Medicine>>

    @POST("add-update-medicine-prescription")
    fun updatePrescription(@Body prescription: Prescription): Call<Prescription>

    @GET("get-prescriptions/{orderAppointmentId}")
    fun getPrescription(@Path("orderAppointmentId") orderAppointmentId: Int): Call<Prescriptions>

    @POST("add-update-labtest-prescription")
    fun updateLabTest(@Body labTest: LabTest): Call<LabTest>

    @POST("get-labtest-list")
    fun getLabTests(@Body search: LabTestSearch): Call<List<LabTest>>

    @POST("add-update-prescription-summary")
    fun updateSummary(@Body summary: Summary): Call<Summary>

    @POST("doctor_delete-medicine-prescription")
    fun deletePrescription(@Body prescription: Prescription): Call<Any>

    @POST("delete-labtest-prescription")
    fun deleteLabTest(@Body labTest: LabTest): Call<Any>

    @POST("get-doctor-time-slots")
    fun getTimeSlots(@Body date: DateRequest): Call<List<TimeSlot>>

    @GET("get-notifications")
    fun getNotifications(): Call<List<Notification>>

    @GET("doctor-profile")
    fun getProfile(): Call<Doctor>

    companion object {
        const val URL_BASE = "http://crm.medexpertz.com/api/"
        const val NO_AUTH = "No-Auth"
        private const val HEADER_NO_AUTH = "$NO_AUTH: true"
    }
}