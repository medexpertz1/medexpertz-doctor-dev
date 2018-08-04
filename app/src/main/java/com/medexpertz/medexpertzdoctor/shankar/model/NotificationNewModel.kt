package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class NotificationNewModel {

    @SerializedName("notification")
    val notification: NotificationNewDataModel? = null
    @SerializedName("appointment")
    val appointment: AppointmentNewModel? = null
}