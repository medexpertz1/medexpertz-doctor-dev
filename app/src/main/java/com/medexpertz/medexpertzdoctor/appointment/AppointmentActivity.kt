package com.medexpertz.medexpertzdoctor.appointment

import android.Manifest.permission.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.inscripts.interfaces.Callbacks
import com.inscripts.interfaces.LaunchCallbacks
import com.medexpertz.medexpertzdoctor.AppointmentActivityBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.R.id.callMenu
import com.medexpertz.medexpertzdoctor.R.menu.call_menu
import com.medexpertz.medexpertzdoctor.auth.WorkTiming
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Status
import com.medexpertz.medexpertzdoctor.prescription.PrescriptionActivity
import com.medexpertz.medexpertzdoctor.shankar.activity.PatientDocumentActivity
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import kotlinx.android.synthetic.main.activity_appointment.*
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class AppointmentActivity : BaseActivity() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    @Inject lateinit var cometChat: CometChat
    private lateinit var mViewModel: AppointmentViewModel
    private lateinit var mBinding: AppointmentActivityBinding
    private var isAudioLaunch: Boolean = false
    private var retrofitDataProvider: RetrofitDataProvider? = null
    private var menu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_appointment)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mBinding.handler = this
        retrofitDataProvider = RetrofitDataProvider(this)

        mViewModel = ViewModelProviders.of(this, mFactory)[AppointmentViewModel::class.java]
       // updatePrescriptionModel = ViewModelProviders.of(this!!, mFactory)[PrescriptionViewModel::class.java]

        mViewModel.initAppointment(intent)
        mViewModel.appointment.observe(this, Observer {
            mBinding.state = it?.status
            if (it?.status == Status.SUCCESS) {
                mBinding.appointment = it.data
            }
        })

        mViewModel.getReasons().observe(this, Observer {
            if (it?.status == Status.SUCCESS) {
                mBinding.reason?.adapter = ReasonAdapter(it.data!!)
            }
        })

        viewDocumentTV.setOnClickListener {
            startActivity(Intent(this, PatientDocumentActivity::class.java)
                    .putExtra("patient_id",mBinding.appointment!!.patientId.toString()))
        }
    }

    fun onLeft() {
        mViewModel.updateAppointmentStatus().observe(this, Observer {
           finish()
        })
    }

    fun onReject() {
        mBinding.slidingUPL.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }

    fun onRejected() {
        val reason = mBinding.reason?.adapter?.getSelectedItem()
        if (reason != null) {
            mViewModel.rejectAppointmentStatus(reason).observe(this, Observer {
                if (it?.status == Status.SUCCESS) {
                    Toast.makeText(this, R.string.rejected_appointment, Toast.LENGTH_SHORT).show()
                    mBinding.slidingUPL.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                }
            })
        } else {
            Toast.makeText(this, R.string.select_reason, Toast.LENGTH_SHORT).show()
        }
    }

    fun onVideoCall(appointment: Appointment) {
        if (hasPermissions()) {
            if (ClsGeneral.getPreferences(this, "icCometChat").equals("false")){
                cometChatLogin( ClsGeneral.getPreferences(this, "comeChatId"), appointment)
            }else {

                launchCometChat(appointment)
            }
//            cometChatLogin(profile.cometId, state)

            /* val intent = Intent(this, VideoCallActivity::class.java)
             intent.putExtra(EXTRA_DATA, appointment)
             intent.putExtra(EXTRA_MEDIA, "Video")
             startActivity(intent)*/
        } else {
            isAudioLaunch = false
            ActivityCompat.requestPermissions(this, PERMISSIONS, RC_PERM)
        }
    }
    fun cometChatLogin(cometUserId: String, appointment: Appointment) {
        cometChat.loginWithUID(this,cometUserId, object : Callbacks {
            override fun successCallback(p0: JSONObject?) {
                launchCometChat(appointment)
            }

            override fun failCallback(p0: JSONObject?) {
            }
        })
    }

    private fun launchCometChat(appointment: Appointment) {
        cometChat.launchCometChat(this, true, appointment.patientCometId/*"B101864417cc5ab4448402ccdb140381"*/, false, true, object : LaunchCallbacks {
            override fun successCallback(jsonObject: JSONObject) {
                println("cometChat : successCallback " + jsonObject.toString())
            }

            override fun failCallback(jsonObject: JSONObject) {
                println("cometChat : failCallback " + jsonObject.toString())
            }

            override fun userInfoCallback(jsonObject: JSONObject) {
                println("cometChat : userInfoCallback " + jsonObject.toString())
                FirebaseMessaging.getInstance().subscribeToTopic(jsonObject!!.getString("push_channel"))
            }

            override fun chatroomInfoCallback(jsonObject: JSONObject) {
                println("cometChat : chatroomInfoCallback " + jsonObject.toString())
            }

            override fun onMessageReceive(jsonObject: JSONObject) {
                println("cometChat : onMessageReceive " + jsonObject.toString())
            }

            override fun error(jsonObject: JSONObject) {
                Timber.e("Onerror responce = $jsonObject")
            }

            override fun onLogout() {

            }

            override fun onWindowClose(jsonObject: JSONObject) {

            }
        })
    }

    private fun hasPermissions(): Boolean {
        PERMISSIONS.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) return false
        }

        return true
    }

    fun onAudioCall(appointment: Appointment) {
       /* if (hasPermissions()) {
            launchCometChat(appointment)*/
            val intent = Intent(this, PrescriptionActivity::class.java)
            intent.putExtra(EXTRA_DATA, appointment)
            intent.putExtra("from", "lab")
            startActivity(intent)
        /*} else {
            isAudioLaunch = true
            ActivityCompat.requestPermissions(this, PERMISSIONS, RC_PERM)
        }*/
    }

    fun onPrescription(appointment: Appointment) {
        val intent = Intent(this, PrescriptionActivity::class.java)
        intent.putExtra(EXTRA_DATA, appointment)
        intent.putExtra("from", "pres")
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (mBinding.slidingUPL.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mBinding.slidingUPL.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty()) {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
            return
        }

        if (isAudioLaunch) {
            onAudioCall(mViewModel.appointment.value?.data!!)
        } else {
            onVideoCall(mViewModel.appointment.value?.data!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.call_menu, menu)
        this.menu = menu.findItem(R.id.callText)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.callMenu) {
            menu!!.setTitle("Calling")
           retrofitDataProvider!!.callExotel( getnumber(ClsGeneral.getPreferences(this, "doc_mobile")),getnumber(mBinding.appointment!!.mobileNo), "08030656694", object : DownlodableCallback<Void> {
                override fun onSuccess(result: Void) {

                }

                override fun onFailure(error: String) {
                }

                override fun onUnauthorized(errorNumber: Int) {

                }
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getnumber(mobileNo: String): String {
        var num = ""
        if (mobileNo.contains("+91")) num = mobileNo.replace("+91", "")
        else if (mobileNo.contains("91")) num = mobileNo.replace("91", "")
        else if(mobileNo.contains("+966")) num = mobileNo.replace("+966", "")
        else if(mobileNo.contains("966")) num = mobileNo.replace("966", "")
        else{
            num = mobileNo
        }
        return  num;

    }


    private companion object {
        const val RC_PERM = 100
        val PERMISSIONS = arrayOf(CAMERA, RECORD_AUDIO, WRITE_EXTERNAL_STORAGE)
    }


}
