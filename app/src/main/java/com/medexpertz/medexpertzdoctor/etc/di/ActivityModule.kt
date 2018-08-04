package com.medexpertz.medexpertzdoctor.etc.di

import com.medexpertz.medexpertzdoctor.MainActivity
import com.medexpertz.medexpertzdoctor.appointment.AppointmentActivity
import com.medexpertz.medexpertzdoctor.appointment.ChangeSlotActivity
import com.medexpertz.medexpertzdoctor.auth.AuthActivity
import com.medexpertz.medexpertzdoctor.auth.CountryListActivity
import com.medexpertz.medexpertzdoctor.auth.LanguageActivity
import com.medexpertz.medexpertzdoctor.auth.SpecializationActivity
import com.medexpertz.medexpertzdoctor.home.HomeActivity
import com.medexpertz.medexpertzdoctor.home.WebActivity
import com.medexpertz.medexpertzdoctor.notification.NotificationActivity
import com.medexpertz.medexpertzdoctor.prescription.PrescriptionActivity
import com.medexpertz.medexpertzdoctor.profile.ProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 06 Jan 2018 at 12:10 PM
 */
@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AuthFragmentModule::class])
    fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector()
    fun contributeSpecializationActivity(): SpecializationActivity

    @ContributesAndroidInjector()
    fun contributeCountryListActivity(): CountryListActivity

    @ContributesAndroidInjector()
    fun contributeLanguageActivity(): LanguageActivity

    @ContributesAndroidInjector(modules = [AppointmentFragmentModule::class])
    fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector()
    fun contributeAppointmentActivity(): AppointmentActivity

    @ContributesAndroidInjector(modules = [PrescriptionFragmentModule::class])
    fun contributePrescriptionActivity(): PrescriptionActivity

    @ContributesAndroidInjector()
    fun contributeWebActivity(): WebActivity

    @ContributesAndroidInjector()
    fun contributeChangeSlotActivity(): ChangeSlotActivity

    @ContributesAndroidInjector()
    fun contributeNotificationActivity(): NotificationActivity

    @ContributesAndroidInjector()
    fun contributeProfileActivity(): ProfileActivity
}
