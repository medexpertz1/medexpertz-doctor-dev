package com.medexpertz.medexpertzdoctor.auth

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.inscripts.interfaces.Callbacks
import com.medexpertz.medexpertzdoctor.etc.common.Repository
import com.medexpertz.medexpertzdoctor.etc.model.ApiService
import com.medexpertz.medexpertzdoctor.etc.model.Preference
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 Apr 2018 at 12:39 PM
 */
@Singleton
class AuthRepository @Inject constructor(
       private val mApp: Application,
        private val mApi: ApiService,
        private val mPref: Preference,
        private val cometChat: CometChat
) : Repository(mApp) {
    fun signIn(auth: Auth, state: MutableLiveData<AuthState>) {
        state.value = AuthState.LOADING
        mApi.signIn(auth).enqueue(object : Callback<ProfileResponse?> {
            override fun onFailure(call: Call<ProfileResponse?>?, t: Throwable?) {
                state.value = AuthState.REQUEST_FAILED
                requestFailed(call)
            }

            override fun onResponse(call: Call<ProfileResponse?>?, response: Response<ProfileResponse?>) {
                if (response.code() == 201) {
                    val profile = response.body()!!
                    profile.password = auth.password
                    mPref.save(profile, true)
                    ClsGeneral.setPreferences(mApp, "session", profile.token.toString())
                    ClsGeneral.setPreferences(mApp, "comeChatId", profile.cometId)
                    ClsGeneral.setPreferences(mApp, "doc_mobile", profile.mobile)
                    cometChatLogin(profile.cometId, state)
                } else {
                    state.value = AuthState.INCORRECT_LOGIN
                }
            }
        })
    }

    fun getSpecializations(specializations: MutableLiveData<Resource<List<Specialization>>>) {
        specializations.value = Resource.loading()
        mApi.getSpecializations().enqueue(object : Callback<List<Specialization>?> {
            override fun onFailure(call: Call<List<Specialization>?>?, t: Throwable?) {
                specializations.value = Resource.error()
                requestFailed(call)
            }

            override fun onResponse(call: Call<List<Specialization>?>?, response: Response<List<Specialization>?>?) {
                specializations.value = Resource.success(response?.body())
            }
        })
    }

    fun getCountryList(countryList: MutableLiveData<Resource<List<Country>>>) {
        countryList.value = Resource.loading()
        mApi.getCountryList().enqueue(object : Callback<List<Country>?> {
            override fun onFailure(call: Call<List<Country>?>?, t: Throwable?) {
                countryList.value = Resource.error()
                requestFailed(call)
            }

            override fun onResponse(call: Call<List<Country>?>?, response: Response<List<Country>?>) {
                countryList.value = Resource.success(response.body())
            }
        })
    }

    fun getLanguages(languages: MutableLiveData<Resource<List<Language>>>) {
        languages.value = Resource.loading()
        mApi.getLanguages().enqueue(object : Callback<List<Language>?> {
            override fun onFailure(call: Call<List<Language>?>?, t: Throwable?) {
                languages.value = Resource.error()
                requestFailed(call)
            }

            override fun onResponse(call: Call<List<Language>?>?, response: Response<List<Language>?>?) {
                languages.value = Resource.success(response!!.body())
            }
        })
    }

    fun register(profile: Profile, state: MutableLiveData<AuthState>) {
        state.value = AuthState.LOADING
        mApi.register(profile).enqueue(object : Callback<Token?> {
            override fun onFailure(call: Call<Token?>?, t: Throwable?) {
                state.value = AuthState.REQUEST_FAILED
                requestFailed(call)
            }

            override fun onResponse(call: Call<Token?>?, response: Response<Token?>) {
                when (response.code()) {
                    201 -> {
                        profile.token = response.body()?.token
                        mPref.save(profile, true)
                        state.value = AuthState.AUTHENTICATED
                        ClsGeneral.setPreferences(mApp,"session",profile.token.toString())
                    }

                    422 -> {
                        state.value = AuthState.DUPLICATE
                    }
                }
            }
        })
    }

    fun cometChatLogin(cometUserId: String, state: MutableLiveData<AuthState>) {
        cometChat.loginWithUID(mApp,cometUserId, object : Callbacks {
            override fun successCallback(p0: JSONObject?) {
                Timber.e("CometChat: Logged in successfully with the id: $cometUserId\n${p0?.toString()}")
                 ClsGeneral.setPreferences(mApp, "icCometChat", "true")
                state.value = AuthState.AUTHENTICATED
            }

            override fun failCallback(p0: JSONObject?) {
                Timber.e("CometChat: Logged in failed with the id: $cometUserId\n${p0?.toString()}")
                ClsGeneral.setPreferences(mApp, "icCometChat", "false")
                state.value = AuthState.AUTHENTICATED
            }
        })
    }
}