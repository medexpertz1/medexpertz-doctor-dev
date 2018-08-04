package com.medexpertz.medexpertzdoctor.auth

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.medexpertz.medexpertzdoctor.CountryListActivityBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.model.Status
import javax.inject.Inject

class CountryListActivity : BaseActivity(), CountryListAdapter.OnCountryItemClick {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: CountryListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_country_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, mFactory)[AuthViewModel::class.java]
        mViewModel.getCountryList().observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                mBinding.adapter = CountryListAdapter(it.data!!, this)
            }
        })
    }

    override fun onCountry(country: Country) {
        val intent = Intent()
        intent.putExtra(EXTRA_DATA, country)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
