package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class NotificationNewDataModel {
    @SerializedName("notification_id")
val notification_id: Int?= null
    @SerializedName("notification")
    val notification: String?= null
    @SerializedName("order_appointment_id")
    val order_appointment_id: String?= null
    @SerializedName("doctor_id")
    val doctor_id: String?= null
    @SerializedName("read_status")
    val read_status: Int?= null
    @SerializedName("created_at")
    val created_at: String?= null
}