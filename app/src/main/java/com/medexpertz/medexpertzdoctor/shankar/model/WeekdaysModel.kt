package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class WeekdaysModel {
    @SerializedName("day_id")
    var day_id: String? = null
    @SerializedName("day_name")
    var day_name: String? = null
    var checked: Boolean = false
}