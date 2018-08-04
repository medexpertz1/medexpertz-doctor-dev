package com.medexpertz.medexpertzdoctor.auth

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.R.id.*
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Preference
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.WorkTimeModel
import com.medexpertz.medexpertzdoctor.shankar.model.WorkTimingRequest
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.worktiming_row.*
import javax.inject.Inject

class WorkTiming: AppCompatActivity() {
    var retrofitDataProvider: RetrofitDataProvider?= null
    var workTimingRequest: WorkTimingRequest?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.worktiming_row)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
        fromTimeValue.setOnClickListener {

            openTimeDialog(fromTimeValue)
        }
        toTimeValue.setOnClickListener {
            openTimeDialog(toTimeValue)
        }

        okButton.setOnClickListener {
            if (fromTimeValue.text.toString().trim().equals("")) Toast.makeText(this,"Select from time", Toast.LENGTH_SHORT).show()
            else if (toTimeValue.text.toString().trim().equals("")) Toast.makeText(this,"Select To time", Toast.LENGTH_SHORT).show()
            else if (durationTV.text.toString().trim().equals("")) Toast.makeText(this,"Enter consultation duration", Toast.LENGTH_SHORT).show()
            else saveTimeSlot()
        }
    }

    private fun saveTimeSlot() {

        var authetication =  "Bearer "+ClsGeneral.getPreferences(this, "session")
        workTimingRequest = WorkTimingRequest()
        workTimingRequest!!.start_time = fromTimeValue.text.toString()
        workTimingRequest!!.end_time = toTimeValue.text.toString()
        workTimingRequest!!.duration = durationTV.text.toString()
        retrofitDataProvider!!.timeSlot(authetication, workTimingRequest!!, object: DownlodableCallback<Void>{
            override fun onSuccess(result: Void) {

            }

            override fun onFailure(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onUnauthorized(errorNumber: Int) {
                /*val time = fromTimeValue.text.toString()+":"+toTimeValue.text.toString()
                val intent = Intent()
                intent.putExtra(BaseActivity.EXTRA_DATA, time)
                setResult(RESULT_OK, intent)
                finish()*/
                Toast.makeText(this@WorkTiming,"Time slot added", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

    }

    private fun openTimeDialog(timeText: TextView?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.time_dialog)
        dialog.setTitle("MedExpertz")
        val ok = dialog.findViewById(R.id.okButton) as Button
        var timePicker = dialog!!.findViewById(R.id.timePicker) as TimePicker
        timePicker.setIs24HourView(true)
        dialog.show()
        ok.setOnClickListener {
            if (timeText != null) {
                timeText.setText(""+ timePicker.currentHour+":"+ (timePicker.currentMinute))
            }
            dialog.dismiss()
        }

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}