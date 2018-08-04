package com.medexpertz.medexpertzdoctor.etc.common

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.medexpertz.medexpertzdoctor.etc.di.Injectable

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 24 Mar 2018 at 4:15 PM
 */
abstract class BaseActivity : AppCompatActivity(), Injectable {
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_SPECIALIZATION = "extra_specialization"
        const val EXTRA_APPOINTMENT_ID = "extra_appointment_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MEDIA = "extra_media"
    }
}