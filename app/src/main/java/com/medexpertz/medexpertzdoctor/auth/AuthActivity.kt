package com.medexpertz.medexpertzdoctor.auth

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.auth.AuthState.*
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class AuthActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        AWSMobileClient.getInstance().initialize(this).execute()

        mViewModel = ViewModelProviders.of(this, mFactory)[AuthViewModel::class.java]
        mViewModel.state.observe(this, Observer { authStateChanged(it!!) })
    }

    private fun authStateChanged(state: AuthState) {
        when (state) {
            INIT -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerCL, LoginFragment())
                    .commit()

            REGISTER -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerCL, Profile1Fragment())
                    .addToBackStack(null)
                    .commit()

            PROFILE_2 -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerCL, Profile2Fragment())
                    .addToBackStack(null)
                    .commit()

            DUPLICATE -> {
                Toast.makeText(this, R.string.duplicate_account, Toast.LENGTH_LONG).show()
                supportFragmentManager.popBackStack()
            }

            INCORRECT_LOGIN -> {
                Toast.makeText(this, R.string.incorrect_email_or_password, Toast.LENGTH_LONG).show()
            }

            AUTHENTICATED -> {
                Toast.makeText(this, R.string.successfully_logged_in, Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }

            else -> {
                // other states are handled in their respective fragments
            }
        }
    }

    override fun supportFragmentInjector() = fragmentInjector
}
