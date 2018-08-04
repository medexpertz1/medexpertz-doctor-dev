package com.medexpertz.medexpertzdoctor.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.SpecializationActivityBinding
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity
import com.medexpertz.medexpertzdoctor.etc.di.ClickedPosition
import com.medexpertz.medexpertzdoctor.etc.model.Status
import javax.inject.Inject

class SpecializationActivity : BaseActivity() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: SpecializationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_specialization)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProviders.of(this, mFactory)[AuthViewModel::class.java]
        mViewModel.getSpecializations().observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                it.data?.let { mBinding.adapter = SpecializationAdapter(it, object : ClickedPosition {
                    override fun onClicked(pos: Int) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_DATA, mBinding.adapter?.getSelectedSpecializations())
                        setResult(RESULT_OK, intent)
                        finish()

                    }
                }) }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.check, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.done) {
            val intent = Intent()
            intent.putExtra(EXTRA_DATA, mBinding.adapter?.getSelectedSpecializations())
            setResult(RESULT_OK, intent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
