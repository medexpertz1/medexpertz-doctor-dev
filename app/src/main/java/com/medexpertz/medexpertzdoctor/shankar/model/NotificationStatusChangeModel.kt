package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class NotificationStatusChangeModel {
    @SerializedName("notification_id")
    var notification_id: Int? = null
    @SerializedName("notification")
    var notification: String? = null
    @SerializedName("order_appointment_id")
    var order_appointment_id: Int? = null
    @SerializedName("doctor_id")
    var doctor_id: String? = null
    @SerializedName("read_status")
    var read_status: String? = null
}