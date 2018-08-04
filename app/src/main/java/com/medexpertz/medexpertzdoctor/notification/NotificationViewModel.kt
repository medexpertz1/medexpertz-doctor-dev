package com.medexpertz.medexpertzdoctor.notification

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.medexpertz.medexpertzdoctor.etc.model.Resource
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 03 May 2018 at 6:34 PM
 */
class NotificationViewModel @Inject constructor(private val mRepo: NotificationRepository) : ViewModel() {
    val notifications = MutableLiveData<Resource<List<Notification>>>()

    fun getNotifications() {
        mRepo.getNotifications(notifications)
    }
}