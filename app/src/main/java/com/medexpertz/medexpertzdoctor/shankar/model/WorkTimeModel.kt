package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class WorkTimeModel(
        @SerializedName("data")
        var data: String?= null,
        @SerializedName("msg")
        var msg: String?= null
)