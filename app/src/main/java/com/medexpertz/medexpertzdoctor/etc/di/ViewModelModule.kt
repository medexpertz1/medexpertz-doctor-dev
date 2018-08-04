package com.medexpertz.medexpertzdoctor.etc.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.medexpertz.medexpertzdoctor.appointment.AppointmentViewModel
import com.medexpertz.medexpertzdoctor.auth.AuthViewModel
import com.medexpertz.medexpertzdoctor.etc.common.ViewModelFactory
import com.medexpertz.medexpertzdoctor.notification.NotificationViewModel
import com.medexpertz.medexpertzdoctor.prescription.PrescriptionViewModel
import com.medexpertz.medexpertzdoctor.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppointmentViewModel::class)
    fun bindAppointmentViewModel(viewModel: AppointmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrescriptionViewModel::class)
    fun bindPrescriptionViewModel(viewModel: PrescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    fun bindNotificcationViewModel(viewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
