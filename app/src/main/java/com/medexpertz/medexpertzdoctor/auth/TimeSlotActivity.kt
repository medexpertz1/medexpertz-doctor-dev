package com.medexpertz.medexpertzdoctor.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.adapter.DoctorTimeSlotAdapter
import com.medexpertz.medexpertzdoctor.shankar.model.DoctorTimeValueModel
import kotlinx.android.synthetic.main.activity_timeslot.*

class TimeSlotActivity: AppCompatActivity() {
    var list: ArrayList<DoctorTimeValueModel>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeslot)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        list = intent.getParcelableArrayListExtra("list")

        timeslotRV.adapter = DoctorTimeSlotAdapter(this, this!!.list!!, object : ClickListener {
            override fun onClick(pos: Int) {
                Log.e("","")
            }

            override fun onDelete(pos: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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