package com.medexpertz.medexpertzdoctor.prescription

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import com.medexpertz.medexpertzdoctor.appointment.Appointment
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity.Companion.EXTRA_DATA
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import javax.inject.Inject

class PrescriptionViewModel @Inject constructor(private val mRepo: PrescriptionRepository) : ViewModel() {
    lateinit var appointment: Appointment
    private val prescriptions = MutableLiveData<Resource<Prescriptions>>()
    val state = MutableLiveData<PrescriptionState>()

    val medicines = MutableLiveData<Resource<List<Medicine>>>()
    val updatePrescription = MutableLiveData<Resource<Prescription>>()
    val deletePrescription = MutableLiveData<Resource<Prescription>>()

    val labTests = MutableLiveData<Resource<List<LabTest>>>()
    val updateLabTest = MutableLiveData<Resource<LabTest>>()
    val deleteLabTest = MutableLiveData<Resource<LabTest>>()

    val updateSummary = MutableLiveData<Resource<Summary>>()

    fun setAppointment(intent: Intent) {
        appointment = intent.getSerializableExtra(EXTRA_DATA) as Appointment
        state.value = PrescriptionState.PRESCRIPTION
    }

    fun updateState(prescriptionState: PrescriptionState) {
        state.value = prescriptionState
    }

    fun getAppointmentDetails(): LiveData<Resource<Prescriptions>> {
        if (prescriptions.value?.data == null) {
            mRepo.getPrescriptions(appointment, prescriptions)
        }

        return prescriptions
    }

    fun searchMedicine(search: String) {
        mRepo.getMedicine(search, medicines)
    }

    fun addPrescription(prescription: Prescription){
        mRepo.addPrescription(prescription, updatePrescription)
    }

    fun addLabTest(labTest: LabTest) {
        mRepo.addLabTest(labTest, updateLabTest)
    }

    fun searchLabTest(search: String) {
        mRepo.getLabTest(search, appointment, labTests)
    }

    fun updateSummary(summary: Summary): LiveData<Resource<Summary>> {
        mRepo.updateSummary(summary, updateSummary)
        return updateSummary
    }

    fun deletePrescription(prescription: Prescription) {
        mRepo.deletePrescription(prescription, deletePrescription)
    }

    fun deleteLabTest(labTest: LabTest) {
        mRepo.deleteLabTest(labTest, deleteLabTest)
    }
}
