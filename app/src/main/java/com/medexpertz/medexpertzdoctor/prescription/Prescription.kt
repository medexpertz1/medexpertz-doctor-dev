package com.medexpertz.medexpertzdoctor.prescription

import com.medexpertz.medexpertzdoctor.appointment.Appointment

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Apr 2018 at 7:59 PM
 */
data class Prescription(
        val medicineId: Int?,
        var orderAppointmentId: Int,
        val medicineName: String,
        var qty: Int,
        var morning: Int,
        var afternoon: Int,
        var evening: Int,
        var afterFood: Int,
        var beforeFood: Int,
        var noOfDays: Int) {
    constructor(item: Medicine, appointment: Appointment) :
            this(null, appointment.orderAppointmentId, item.medicineName,
                    0, 0, 0, 0, 0, 0, 0)

    fun whence() = if (afterFood == 1) "After Food" else "Before Food"

    fun isValid(): Boolean {
        return qty > 0 && (morning > 0 || afternoon > 0 || evening > 0) && (afterFood == 1 || beforeFood == 1) && noOfDays > 0
    }

    fun qtyString() = qty.toString()
}