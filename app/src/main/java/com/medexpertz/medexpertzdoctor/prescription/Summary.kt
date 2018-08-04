package com.medexpertz.medexpertzdoctor.prescription

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 6:18 PM
 */
data class Summary(
        val summaryId: Int? = null,
        var orderAppointmentId: Int? = null,
        var summary: String? = null
)