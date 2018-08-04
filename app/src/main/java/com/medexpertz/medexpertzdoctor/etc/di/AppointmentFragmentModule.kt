package com.medexpertz.medexpertzdoctor.etc.di

import com.medexpertz.medexpertzdoctor.appointment.AppointmentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppointmentFragmentModule {
    @ContributesAndroidInjector
    fun contributeAppointmentFragment(): AppointmentFragment
}
