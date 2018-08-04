package com.medexpertz.medexpertzdoctor.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.fragment_login.*

class ForgotPassword:AppCompatActivity() {
    var retrofitDataProvider: RetrofitDataProvider?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forotpassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)

        signInCV.setOnClickListener {
            if (emailET.text.toString().trim().equals("")){
                Toast.makeText(this, resources.getString(R.string.email_id), Toast.LENGTH_SHORT).show()
            }else
            {
                loaderLIV.visibility = View.VISIBLE
                val auth = "Bearer " + ClsGeneral.getPreferences(this, "session")
                retrofitDataProvider!!.forgotPassword(auth, emailET.text.toString(), object : DownlodableCallback<Void> {
                    override fun onSuccess(result: Void) {

                    }

                    override fun onFailure(error: String) {
                    }

                    override fun onUnauthorized(errorNumber: Int) {
                        if (errorNumber == 200)
                        {
                            Toast.makeText(this@ForgotPassword, resources.getString(R.string.passwordsenttoyoyrmail), Toast.LENGTH_SHORT).show()
                            loaderLIV.visibility = View.GONE
                            finish()
                        }
                    }

                })
            }
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