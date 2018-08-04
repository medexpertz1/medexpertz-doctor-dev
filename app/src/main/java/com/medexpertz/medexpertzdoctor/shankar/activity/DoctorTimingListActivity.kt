package com.medexpertz.medexpertzdoctor.shankar.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.auth.TimeSlotActivity
import com.medexpertz.medexpertzdoctor.auth.WorkTiming
import com.medexpertz.medexpertzdoctor.notification.NotificationActivity
import com.medexpertz.medexpertzdoctor.shankar.adapter.DoctorTimingListAdapter
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.DoctorTimeLisrModel
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_timimglistdetails.*

class DoctorTimingListActivity: AppCompatActivity(){
    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timimglistdetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        timelsidtRV.layoutManager = LinearLayoutManager(this)
        retrofitDataProvider = RetrofitDataProvider(this)



    }

    override fun onResume() {
        super.onResume()
        getDoctorTime()
    }

    private fun getDoctorTime() {
        retrofitDataProvider!!.doctorTimeList("Bearer "+ClsGeneral.getPreferences(this, "session"), "", object : DownlodableCallback<ArrayList<DoctorTimeLisrModel>> {
            override fun onSuccess(result: ArrayList<DoctorTimeLisrModel>) {
                timelsidtRV.adapter = DoctorTimingListAdapter(this@DoctorTimingListActivity, result, object : ClickListener {
                    override fun onClick(pos: Int) {

                        startActivity(Intent(this@DoctorTimingListActivity, TimeSlotActivity::class.java)
                                .putParcelableArrayListExtra("list", result[pos].values))
                   }

                    override fun onDelete(pos: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }

            override fun onFailure(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onUnauthorized(errorNumber: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.add_time_slot, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        if (item?.itemId == R.id.addslot) {
            startActivity(Intent(this, WorkTiming::class.java))
            return true
        }


        return super.onOptionsItemSelected(item)
    }
}