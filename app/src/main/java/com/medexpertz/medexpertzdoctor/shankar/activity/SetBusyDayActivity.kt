package com.medexpertz.medexpertzdoctor.shankar.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.adapter.DaysAdapter
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.DaysSelection
import com.medexpertz.medexpertzdoctor.shankar.model.WeekdaysModel
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_busyday.*

class SetBusyDayActivity: AppCompatActivity(){
    var retrofitDataProvider: RetrofitDataProvider?= null
    var weekDaysId = ArrayList<DaysSelection>()
    var id = ArrayList<String>()
    var adapterr:DaysAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busyday)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        daysRV.layoutManager = GridLayoutManager(this, 3)
        retrofitDataProvider = RetrofitDataProvider((this))

        getWeekdaysData()
        updateBusy.setOnClickListener {
            if (weekDaysId.size==0){
                Toast.makeText(this, "Please select time", Toast.LENGTH_SHORT).show()
            }
            else{
                for (i in 0 until weekDaysId.size) {

                    id.add(weekDaysId[i].id)
                }
                updateBusyApi()
            }
        }

    }

    private fun updateBusyApi() {

        retrofitDataProvider!!.updateWeekOff("Bearer "+ClsGeneral.getPreferences(this,"session"),id, object : DownlodableCallback<Void> {
            override fun onSuccess(result: Void) {


            }

            override fun onFailure(error: String) {
            }

            override fun onUnauthorized(errorNumber: Int) {
                finish()
            }

        })

    }

    private fun getWeekdaysData() {

        retrofitDataProvider!!.getWeekDays("Bearer "+ClsGeneral.getPreferences(this,"session"), object : DownlodableCallback<ArrayList<WeekdaysModel>> {
            override fun onSuccess(result: ArrayList<WeekdaysModel>) {
                 adapterr = DaysAdapter(this@SetBusyDayActivity, result, object : ClickListener {
                    override fun onClick(pos: Int) {
                        if (result[pos].checked){
                            result[pos].checked = false
                            weekDaysId.remove(DaysSelection(pos, result[pos].day_id!!))
                        }
                        else{
                            result[pos].checked = true
                            try {
                                weekDaysId.add(DaysSelection(pos, result[pos].day_id!!))
                            }
                            catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                        adapterr!!.notifyDataSetChanged()

                    }

                    override fun onDelete(pos: Int) {
                    }
                })
                daysRV.adapter = adapterr
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