package com.medexpertz.medexpertzdoctor.shankar.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import com.bigappcompany.medexpertz.shankar.interfaces.ClickListener
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Preference
import com.medexpertz.medexpertzdoctor.shankar.adapter.DocumentListAdapter
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.PatientDocumentModel
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_patientdocument.*
import javax.inject.Inject

class PatientDocumentActivity: AppCompatActivity(){
    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientdocument)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
        val patient_id = intent.getStringExtra("patient_id")
        retrofitDataProvider!!.patientDocument(ClsGeneral.getPreferences(this, "session"),patient_id, object : DownlodableCallback<ArrayList<PatientDocumentModel>> {
            override fun onSuccess(result: ArrayList<PatientDocumentModel>) {
                Log.e("response", result.toString())
                DocumentRV.layoutManager = GridLayoutManager(this@PatientDocumentActivity, 2)
                DocumentRV.adapter = DocumentListAdapter(this@PatientDocumentActivity, result, object : ClickListener {
                    override fun onClick(pos: Int) {

                        startActivity(Intent(this@PatientDocumentActivity, DocumentFullScreen::class.java)
                                .putExtra("image", result.get(pos).files!![0].file_path))
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