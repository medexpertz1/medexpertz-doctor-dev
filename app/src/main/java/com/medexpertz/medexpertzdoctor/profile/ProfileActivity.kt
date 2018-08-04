package com.medexpertz.medexpertzdoctor.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.medexpertz.medexpertzdoctor.ProfileActivityBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.auth.*
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Status
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : BaseActivity() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: ProfileViewModel
    private lateinit var mBinding: ProfileActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.handler = this
        mBinding.edit = false
        mViewModel = ViewModelProviders.of(this, mFactory)[ProfileViewModel::class.java]
        mViewModel.getProfile().observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                mBinding.doc = it.data
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        when (requestCode) {
            RC_SPECIALIZATION -> {
                val specializations = data!!.getSerializableExtra(EXTRA_DATA) as ArrayList<Specialization>
                mViewModel.setSpecializations(specializations)
            }

            RC_LANGUAGE -> {
                val languages = data!!.getSerializableExtra(EXTRA_DATA) as ArrayList<Language>
                mViewModel.setLanguages(languages)
            }
        }
    }

    fun onLanguage() {
        startActivityForResult(Intent(this, LanguageActivity::class.java), RC_LANGUAGE)
    }

    fun onSpecialization() {
        startActivityForResult(Intent(this, SpecializationActivity::class.java), RC_SPECIALIZATION)
    }


    fun onAvatar() {

    }

    private companion object {
        const val RC_SPECIALIZATION = 10
        const val RC_LANGUAGE = 30
    }
}
