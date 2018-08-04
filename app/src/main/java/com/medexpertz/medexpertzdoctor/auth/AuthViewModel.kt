package com.medexpertz.medexpertzdoctor.auth

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import java.util.*
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 Apr 2018 at 12:28 PM
 */
class AuthViewModel @Inject constructor(private val mRepo: AuthRepository) : ViewModel() {
    val auth = MutableLiveData<Auth>()
    val state = MutableLiveData<AuthState>()
    val profile = MutableLiveData<Profile>()

    private val specializations = MutableLiveData<Resource<List<Specialization>>>()
    private val countryList = MutableLiveData<Resource<List<Country>>>()
    private val languages = MutableLiveData<Resource<List<Language>>>()

    fun signIn(): LiveData<AuthState> {
        mRepo.signIn(auth.value!!, state)
        return state
    }

    fun navigateToRegister() {
        state.value = AuthState.REGISTER
    }

    fun getSpecializations(): LiveData<Resource<List<Specialization>>> {
        mRepo.getSpecializations(specializations)
        return specializations
    }

    fun setProfileImageUrl(url: String) {
        val user = profile.value
        user?.profilePic = url
        profile.value = user
    }

    fun profile1Completed() {
        state.value = AuthState.PROFILE_2
    }

    fun getCountryList(): LiveData<Resource<List<Country>>> {
        mRepo.getCountryList(countryList)
        return countryList
    }

    fun setCountry(country: Country) {
        profile.value?.countryId = country.id
        profile.value?.dialCode = country.dialCode
        profile.value?.countryName = country.countryName
        profile.value = profile.value
    }

    fun setSpecializations(specializations: ArrayList<Specialization>) {
        profile.value?.specialization = specializations.map { specialization -> specialization.id }
        profile.value?.specializationName =  specializations[0].specialization
        profile.value = profile.value
    }

    fun getLanguages(): LiveData<Resource<List<Language>>> {
        mRepo.getLanguages(languages)
        return languages
    }

    fun setLanguages(languages: ArrayList<Language>) {
        profile.value?.knownLanguages = languages.map { language -> language.languageId }
        profile.value?.languageList(languages)
        profile.value = profile.value
    }
    fun setTiming(timing: String) {
        profile.value?.timing = timing
        profile.value = profile.value
    }

    fun register(): LiveData<AuthState> {
        mRepo.register(profile.value!!, state)
        return state
    }
    fun setFcmToken(fcmToken: String){
        profile.value?.fcm_token = fcmToken
        profile.value = profile.value
    }
    fun setLoginFcmToken(fcmToken: String){
        auth.value?.fcm_token = fcmToken
        auth.value = auth.value
    }

    init {
        auth.value = Auth()
        state.value = AuthState.INIT
        profile.value = Profile()
    }
}