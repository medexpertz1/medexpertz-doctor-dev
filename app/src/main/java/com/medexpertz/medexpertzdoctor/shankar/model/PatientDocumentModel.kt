package com.medexpertz.medexpertzdoctor.shankar.model

import com.google.gson.annotations.SerializedName

class PatientDocumentModel {
    @SerializedName("document_id")
    var document_id: String? = null
    @SerializedName("report_title")
    var report_title: String? = null
    @SerializedName("date_of_report")
    var date_of_report: String? = null
    @SerializedName("files")
    var files: ArrayList<PatientDocumentFilesModel>? = null
}