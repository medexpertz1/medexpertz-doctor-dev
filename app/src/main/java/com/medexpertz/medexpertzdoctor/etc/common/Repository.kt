package com.medexpertz.medexpertzdoctor.etc.common

import android.app.Application
import android.support.annotation.StringRes
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.R
import retrofit2.Call
import timber.log.Timber

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Apr 2018 at 10:03 AM
 */
abstract class Repository(private val app: Application) {
    fun <T> requestFailed(call: Call<T?>?, t: Throwable? = null) {
        if (call?.isExecuted == false) Toast.makeText(app, R.string.no_internet, Toast.LENGTH_SHORT).show()
        Timber.e(t)
    }

    fun longToast(@StringRes res: Int) = Toast.makeText(app, res, Toast.LENGTH_LONG).show()
}