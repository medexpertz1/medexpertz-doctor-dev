package com.medexpertz.medexpertzdoctor.notification

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
 * @created on 03 May 2018 at 6:36 PM
 */
@Singleton
class NotificationRepository @Inject constructor(app: Application, private val mApi: ApiService) : Repository(app) {
    fun getNotifications(notifications: MutableLiveData<Resource<List<Notification>>>) {
        notifications.value = Resource.loading()
        mApi.getNotifications().enqueue(object : Callback<List<Notification>?> {
            override fun onFailure(call: Call<List<Notification>?>?, t: Throwable?) {
                notifications.value = Resource.error()
                requestFailed(call, t)
            }

            override fun onResponse(call: Call<List<Notification>?>?, response: Response<List<Notification>?>) {
                if (response.code() == 200) {
                    notifications.value = Resource.success(response.body())
                } else {
                    notifications.value = Resource.success(ArrayList())
                }
            }
        })
    }
}