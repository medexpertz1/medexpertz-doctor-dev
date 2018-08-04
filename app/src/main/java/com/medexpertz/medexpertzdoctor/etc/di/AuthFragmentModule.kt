package com.medexpertz.medexpertzdoctor.etc.di

import com.medexpertz.medexpertzdoctor.auth.LoginFragment
import com.medexpertz.medexpertzdoctor.auth.Profile1Fragment
import com.medexpertz.medexpertzdoctor.auth.Profile2Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 06 Jan 2018 at 12:13 PM
 */
@Module
abstract class AuthFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeProfile1Fragment(): Profile1Fragment

    @ContributesAndroidInjector
    abstract fun contributeProfile2Fragment(): Profile2Fragment
}