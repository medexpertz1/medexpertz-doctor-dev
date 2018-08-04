package com.medexpertz.medexpertzdoctor.prescription

data class Prescriptions(
        val medicines: ArrayList<Prescription>?,
        val labtests: ArrayList<LabTest>?,
        val summary: ArrayList<Summary>?
)
