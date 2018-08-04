package com.medexpertz.medexpertzdoctor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.medexpertz.medexpertzdoctor.auth.AuthActivity
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Preference
import com.medexpertz.medexpertzdoctor.home.HomeActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject lateinit var mPref: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (mPref.isLoggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivityForResult(Intent(this, AuthActivity::class.java), RC_AUTH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_AUTH && resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        finish()
    }

    companion object {
        private const val RC_AUTH = 100
    }
}
