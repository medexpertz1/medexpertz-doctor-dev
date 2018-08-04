package com.bigappcompany.medexpertz.shankar.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.androidquery.AQuery
import com.bigappcompany.medexpertz.shankar.fragment.UsersFragment
import com.firebase.client.Firebase
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.shankar.Utilz.PreferenceName
import com.medexpertz.medexpertzdoctor.shankar.Utilz.Utils
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import com.medexpertz.medexpertzdoctor.shankar.subtabs.SubTabDetails
import com.medexpertz.medexpertzdoctor.shankar.subtabs.SubTabLayout
import com.medexpertz.medexpertzdoctor.shankar.subtabs.SubTabsAdapter
import kotlinx.android.synthetic.main.activity_listfirebase.*
import java.util.ArrayList
import java.util.prefs.Preferences

class FirebaseUserListActivity: AppCompatActivity() {
     var mSubTabsAdapter: SubTabsAdapter?= null
     var aq: AQuery?= null
    private var slidingTabLayout: SubTabLayout? = null
    private var viewPager: ViewPager? = null
    private var items: ArrayList<SubTabDetails>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listfirebase)
        Firebase.setAndroidContext(this)
        aq = AQuery(this)
        userName.text = ClsGeneral.getPreferences(this, PreferenceName.USER_NAME)


        val list = ArrayList<SubTabDetails>()
        list.add(SubTabDetails("", "1001", UsersFragment()))
        val mSubTabsAdapter = SubTabsAdapter(supportFragmentManager, list)
        pager.adapter =mSubTabsAdapter
    }

    override fun onResume() {
        super.onResume()
        Firebase.goOnline()
            Utils.updateUserStatus(this, PreferenceName.ONLINE)

    }

    override fun onStop() {
        super.onStop()
            Utils.updateUserStatus(this, Utils.getFirebaseCurrentDate())
    }
}