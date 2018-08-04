package com.medexpertz.medexpertzdoctor.notification

import com.medexpertz.medexpertzdoctor.appointment.Appointment

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 03 May 2018 at 6:13 PM
 */
data class Notification(
        val notification: Content,
        val appointment: Appointment
)

data class Content(
        val notification: String,
        val createdAt: String,
        val notificationId: String,
        val orderAppointmentId: String,
        val doctorId: String,
        val readStatus: String
)