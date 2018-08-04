package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class PatientDocumentFilesModel {
    @SerializedName("file_id")
    var file_id: String? = null
    @SerializedName("file_path")
    var file_path: String? = null
}