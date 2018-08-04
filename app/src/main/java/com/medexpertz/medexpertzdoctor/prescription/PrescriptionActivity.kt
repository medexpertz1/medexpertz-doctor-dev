package com.medexpertz.medexpertzdoctor.prescription

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.prescription.PrescriptionState.*
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class PrescriptionActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: PrescriptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mViewModel = ViewModelProviders.of(this, mFactory)[PrescriptionViewModel::class.java]
        mViewModel.setAppointment(intent)
        mViewModel.state.observe(this, Observer { stateChanged(it!!) })

        if (intent.getStringExtra("from").equals("lab")){
            mViewModel.updateState(PrescriptionState.LAB)
        }
    }

    private fun stateChanged(state: PrescriptionState) {
        when (state) {
            PRESCRIPTION -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFL, PrescriptionFragment())
                    .commit()
            LAB -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFL, LabTestFragment())
                    .addToBackStack(null)
                    .commit()
            SUMMARY -> supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFL, SummaryFragment())
                    .addToBackStack(null)
                    .commit()
            FINISHED -> finish()
        }
    }

    override fun supportFragmentInjector() = mFragmentInjector
}
