package com.medexpertz.medexpertzdoctor.appointment

import android.text.format.DateFormat
import java.io.Serializable
import java.util.*

data class Appointment(
        val orderAppointmentId: Int,
        val appointmentDate: Date,
        val appointmentTime: String,
        val symptomsSummary: String?,
        val totalAmount: Double,
        val specialization: String,
        var appointmentStatus: Int,
        val patientId: Int,
        val patientName: String,
        val gender: String,
        val age: Date,
        val profilePic: String?,
        val bookedForRelation: String,
        var reasonTitle: String? = null,
        val mobileNo: String,
        val patientCometId: String,
        val healthIssues: String?,
        val allergicMedicine: String?
) : Serializable {
    fun displayDate() = DateFormat.format("dd/MM/yyyy", appointmentDate)

//    fun displayDateTime() = "${displayDate()}, $appointmentTime"
    fun displayDateTime() = "${displayDate()}, $appointmentTime"

    fun displayAge(): Int {
        val today = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.time = age

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    fun getAction() = when (appointmentStatus) {
        1 -> "Accept"
        2 -> "Start"
        3 -> "complete"
        else -> null
    }
}
