package com.medexpertz.medexpertzdoctor.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.medexpertz.medexpertzdoctor.auth.Language
import com.medexpertz.medexpertzdoctor.auth.Specialization
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 May 2018 at 1:26 PM
 */
class ProfileViewModel @Inject constructor(private val mRepo: ProfileRepository) : ViewModel() {
    private val profile = MutableLiveData<Resource<Doctor>>()

    fun getProfile(): LiveData<Resource<Doctor>> {
        mRepo.getProfile(profile)
        return profile
    }

    fun setSpecializations(specializations: ArrayList<Specialization>) {
        val doc = profile.value
        // doc?.data?.specialization = specializations
        profile.value = doc
    }

    fun setLanguages(languages: ArrayList<Language>) {
        val doc = profile.value
        doc?.data?.knownLanguages = languages
        profile.value = doc
    }
}