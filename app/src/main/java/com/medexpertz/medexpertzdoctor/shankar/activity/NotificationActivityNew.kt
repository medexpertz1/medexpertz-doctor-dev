package com.medexpertz.medexpertzdoctor.shankar.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.auth.WorkTiming
import com.medexpertz.medexpertzdoctor.notification.NotificationAdapter
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationNewModel
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationStatusChangeModel
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_notification_new.*

class NotificationActivityNew: AppCompatActivity() {
    private var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_new   )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        retrofitDataProvider = RetrofitDataProvider(this)
        scheduleVP.layoutManager = LinearLayoutManager(this)

        notificationApi()

    }

    private fun notificationApi() {

        val auth = "Bearer " + ClsGeneral.getPreferences(this, "session")
        retrofitDataProvider!!.getNotification(auth, object : DownlodableCallback<ArrayList<NotificationNewModel>> {
            override fun onSuccess(result: ArrayList<NotificationNewModel>) {
                scheduleVP.adapter = com.medexpertz.medexpertzdoctor.shankar.adapter.NotificationAdapter(this@NotificationActivityNew,result, object : ClickListener {
                    override fun onClick(pos: Int) {
                        retrofitDataProvider!!.notificationStatusChange(auth, ""+pos, object : DownlodableCallback<NotificationStatusChangeModel> {
                            override fun onSuccess(result: NotificationStatusChangeModel) {

                                notificationApi()
                            }

                            override fun onFailure(error: String) {
                            }

                            override fun onUnauthorized(errorNumber: Int) {
                            }

                        })

                    }

                    override fun onDelete(pos: Int) {
                    }
                })
            }


            override fun onFailure(error: String) {
            }

            override fun onUnauthorized(errorNumber: Int) {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
