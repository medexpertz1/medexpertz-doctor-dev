package com.medexpertz.medexpertzdoctor.etc.di

import com.medexpertz.medexpertzdoctor.prescription.LabTestFragment
import com.medexpertz.medexpertzdoctor.prescription.PrescriptionFragment
import com.medexpertz.medexpertzdoctor.prescription.SummaryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PrescriptionFragmentModule {
    @ContributesAndroidInjector
    fun contributePrescriptionFragment(): PrescriptionFragment

    @ContributesAndroidInjector
    fun contributeLabTestFragment(): LabTestFragment

    @ContributesAndroidInjector
    fun contributeSummaryFragment(): SummaryFragment
}
