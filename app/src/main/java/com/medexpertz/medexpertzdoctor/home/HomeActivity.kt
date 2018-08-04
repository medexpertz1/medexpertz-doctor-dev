package com.medexpertz.medexpertzdoctor.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.bigappcompany.medexpertz.shankar.activity.FirebaseUserListActivity
import com.bigappcompany.medexpertz.shankar.model.FirebaseUserAuthModel
import com.firebase.client.Firebase
import com.medexpertz.medexpertzdoctor.BuildConfig
import com.medexpertz.medexpertzdoctor.MainActivity
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.appointment.AppointmentFragment
import com.medexpertz.medexpertzdoctor.auth.WorkTiming
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.common.ViewPagerAdapter
import com.medexpertz.medexpertzdoctor.etc.model.Preference
import com.medexpertz.medexpertzdoctor.notification.NotificationActivity
import com.medexpertz.medexpertzdoctor.profile.ProfileActivity
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName
import com.medexpertz.medexpertzdoctor.shankar.activity.DoctorTimingListActivity
import com.medexpertz.medexpertzdoctor.shankar.activity.NotificationActivityNew
import com.medexpertz.medexpertzdoctor.shankar.activity.SetBusyDayActivity
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.model.NotificationNewModel
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.DownlodableCallback
import com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork.RetrofitDataProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.navigation_drawer.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject lateinit var mPref: Preference
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    private var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        retrofitDataProvider = RetrofitDataProvider(this)

        setNavigationDrawer()

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addItem(AppointmentFragment.newInstance(1), "New")
        adapter.addItem(AppointmentFragment.newInstance(2), "Accepted")
        adapter.addItem(AppointmentFragment.newInstance(3), "Ongoing")
        adapter.addItem(AppointmentFragment.newInstance(4), "Completed")
        adapter.addItem(AppointmentFragment.newInstance(5), "Rejected")
        homeVP.adapter = adapter
        tabLayout.setupWithViewPager(homeVP)

        profileTV.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            drawerDL.closeDrawer(GravityCompat.START)
        }

        termsTV.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra(EXTRA_TITLE, getString(R.string.terms_and_conditions))
            intent.putExtra(EXTRA_DATA, "http://medexpertz.com/terms-and-conditions/")
            startActivity(intent)
            drawerDL.closeDrawer(GravityCompat.START)
        }
        sirebaseChatTV.setOnClickListener {
            retrofitDataProvider!!.getUserFromFirebase(object : DownlodableCallback<FirebaseUserAuthModel> {
                override fun onSuccess(result: FirebaseUserAuthModel) {

                }

                override fun onFailure(error: String) { }

                override fun onUnauthorized(errorNumber: Int) {
                    if (errorNumber==200)
                    {
                        startActivity(Intent(this@HomeActivity, FirebaseUserListActivity::class.java))
                    }
                    else{
                        registerThisUser()
                    }
                }

            })
        }


        privacyTV.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra(EXTRA_TITLE, getString(R.string.privacy))
            intent.putExtra(EXTRA_DATA, "http://medexpertz.com/privacy-policy/")
            startActivity(intent)
            drawerDL.closeDrawer(GravityCompat.START)
        }

        shareTV.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?labtestId=$packageName")
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Please click below link to download MedExpertz- Doctor App \nhttp://bit.ly/2LFZaFK")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            drawerDL.closeDrawer(GravityCompat.START)
        }

        contactUsTV.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra(EXTRA_TITLE, getString(R.string.contact_us))
            intent.putExtra(EXTRA_DATA, "http://medexpertz.com/about-us/contact-us/")
            startActivity(intent)
            drawerDL.closeDrawer(GravityCompat.START)
        }
        timeSlotTV.setOnClickListener {
            startActivity(Intent(this, DoctorTimingListActivity::class.java))
            drawerDL.closeDrawer(GravityCompat.START)
        }
        weekDaysTV.setOnClickListener {
            startActivity(Intent(this, SetBusyDayActivity::class.java))
            drawerDL.closeDrawer(GravityCompat.START)
        }

        logoutTV.setOnClickListener {
           /* AlertDialog.Builder(this, R.style.DialogTheme)
                    .setTitle(R.string.title_logout)
                    .setMessage(R.string.msg_logout)
                    .setPositiveButton(R.string.logout, { _, _ ->
                        mPref.clear()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .create()
                    .show()*/
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.logout_dialog)
            dialog.setTitle("MedExpertz")
            val cancelTV = dialog.findViewById(R.id.cancelTV) as TextView
            val logoutTV = dialog.findViewById(R.id.logoutTV) as TextView
            dialog.show()
            logoutTV.setOnClickListener {
                dialog.dismiss()
                mPref.clear()
                startActivity(Intent(this, MainActivity::class.java))
            }
            cancelTV.setOnClickListener {
                dialog.dismiss()
            }
            drawerDL.closeDrawer(GravityCompat.START)
        }

        val a =  ClsGeneral.getPreferences(this,"token")
        versionTV.text = getString(R.string.version_name, BuildConfig.VERSION_NAME)
        notificationIV.setOnClickListener {
            startActivity(Intent(this, NotificationActivityNew::class.java))
        }

        helpChat.setOnClickListener {
            homeVP.currentItem = 2
            tabLayout.setupWithViewPager(homeVP)
        }

    }

    private fun registerThisUser() {
            var mStrPhoneNo = ClsGeneral.getPreferences(this, PreferenceName.USER_MOBILE)
            val reference = Firebase(PreferenceName.FIREBASE_DOLCTOR_URL+PreferenceName.FIREBASE_DOLCTORS_TABLE_NAME)
            reference.child(mStrPhoneNo).child("name").setValue(ClsGeneral.getPreferences(this, PreferenceName.USER_NAME))
            reference.child(mStrPhoneNo).child("phone").setValue(ClsGeneral.getPreferences(this, PreferenceName.USER_MOBILE))
            reference.child(mStrPhoneNo).child("email").setValue(ClsGeneral.getPreferences(this, PreferenceName.USER_EMAIL))

            Handler().postDelayed({
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, FirebaseUserListActivity::class.java))
                finish()
            }, 500)
    }

    private fun setNavigationDrawer() {
        drawerDL.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawer: View, slideOffset: Float) {
                contentCL.x = drawer.width * slideOffset
                val lp = contentCL.layoutParams as LinearLayout.LayoutParams
                lp.height = drawer.height - (drawer.height * slideOffset * 0.3f).toInt()
                lp.topMargin = (drawer.height - lp.height) / 2
                contentCL.layoutParams = lp
            }

            override fun onDrawerClosed(drawerView: View) {
            }
        })
        val toggle = ActionBarDrawerToggle(
                this, drawerDL, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerDL.addDrawerListener(toggle)
        drawerDL.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent))
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerDL.isDrawerOpen(GravityCompat.START)) {
            drawerDL.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_notification) {
            startActivity(Intent(this, NotificationActivityNew::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
*/
    override fun supportFragmentInjector() = fragmentInjector
    override fun onResume() {
        super.onResume()
        notificationApi()
    }
    private fun notificationApi() {
        var count: Int? = 0
        val auth = "Bearer " + ClsGeneral.getPreferences(this, "session")
        retrofitDataProvider!!.getNotification(auth, object : DownlodableCallback<ArrayList<NotificationNewModel>> {
            override fun onSuccess(result: ArrayList<NotificationNewModel>) {
                for (i in 0 until result.size) {
                    if (result[i].notification!!.read_status == 0) {
                        count = count!! + 1
                    }
                }

                if (count!! ==0){
                    notificationCount.visibility = View.GONE
                }
                notificationCount.text = count.toString()
            }


            override fun onFailure(error: String) {
            }

            override fun onUnauthorized(errorNumber: Int) {

            }
        })

    }
}
