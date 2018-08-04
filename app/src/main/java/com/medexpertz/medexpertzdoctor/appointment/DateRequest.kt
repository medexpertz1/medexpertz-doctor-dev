package com.medexpertz.medexpertzdoctor.appointment

import android.text.format.DateFormat
import java.util.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 03 May 2018 at 3:21 PM
 */
data class DateRequest(val date: Calendar) {
    fun displayDate() = DateFormat.format("dd MMM yyyy", date).toString()

    fun decrement() {
        date.add(Calendar.DAY_OF_YEAR, -1)
    }

    fun increment() {
        date.add(Calendar.DAY_OF_YEAR, 1)
    }
}