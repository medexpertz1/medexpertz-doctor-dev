package com.medexpertz.medexpertzdoctor.profile

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.medexpertz.medexpertzdoctor.etc.common.Repository
import com.medexpertz.medexpertzdoctor.etc.model.ApiService
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 May 2018 at 3:28 PM
 */
@Singleton
class ProfileRepository @Inject constructor(app: Application, private val mApi: ApiService) : Repository(app) {
    fun getProfile(profile: MutableLiveData<Resource<Doctor>>) {
        profile.value = Resource.loading()
        mApi.getProfile().enqueue(object : Callback<Doctor?> {
            override fun onFailure(call: Call<Doctor?>?, t: Throwable?) {
                profile.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<Doctor?>?, response: Response<Doctor?>) {
                profile.value = Resource.success(response.body())
            }
        })
    }
}