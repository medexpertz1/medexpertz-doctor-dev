package com.medexpertz.medexpertzdoctor.auth

import android.app.Activity.*
import android.app.DatePickerDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import com.medexpertz.medexpertzdoctor.Profile2FragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseActivity.Companion.EXTRA_DATA
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 13 Apr 2018 at 1:03 PM
 */
class Profile2Fragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: Profile2FragmentBinding
    private var mDisposable: Disposable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_2, container, false)
        mBinding.handler = this
        mBinding.validate = true
        return mBinding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = ViewModelProviders.of(activity!!, mFactory)[AuthViewModel::class.java]
        mViewModel.profile.observe(this, Observer { mBinding.profile = it })

        val exp = RxTextView.afterTextChangeEvents(mBinding.experienceET).skipInitialValue()
        val edu = RxTextView.afterTextChangeEvents(mBinding.educationET).skipInitialValue()
        val fee = RxTextView.afterTextChangeEvents(mBinding.feeET).skipInitialValue()

        mViewModel.setFcmToken(ClsGeneral.getPreferences(activity, "fcm_token"))

        mDisposable = Observable.combineLatest(listOf(exp, edu, fee), { mBinding.validate = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun onDatePicker() {
       /* val cal = Calendar.getInstance()
        DatePickerDialog(context!!, R.style.DialogTheme, DatePickerDialog.OnDateSetListener({ _, year, month, dayOfMonth ->
            mBinding.experienceET.setText(getString(R.string.format_dob, year, month + 1, dayOfMonth))
        }), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()*/
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.calendar_dialog)
        dialog.setTitle("MedExpertz")
        val ok = dialog.findViewById(R.id.okButton) as Button
        var selectdateTV = dialog!!.findViewById(R.id.selectdateTV) as DatePicker
        dialog.show()
        ok.setOnClickListener {
            mBinding.experienceET.setText(""+ selectdateTV.year+"-"+ (selectdateTV.month + 1)+"-"+selectdateTV.dayOfMonth)
            dialog.dismiss()
        }
    }

    fun onSpecialization() {
        startActivityForResult(Intent(context!!, SpecializationActivity::class.java), RC_SPECIALIZATION)
    }

    fun onCountry() {
        startActivityForResult(Intent(context!!, CountryListActivity::class.java), RC_COUNTRY)
    }
    fun onTiming() {
        startActivityForResult(Intent(context!!, WorkTiming::class.java), RC_TIMING)
    }

    fun onLanguage() {
        startActivityForResult(Intent(context!!, LanguageActivity::class.java), RC_LANGUAGE)
    }

    fun onSubmit() {
        mViewModel.register().observe(this, Observer {
            mBinding.state = it
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        when (requestCode) {
            RC_COUNTRY -> {
                val country = data!!.getSerializableExtra(EXTRA_DATA) as Country
                mViewModel.setCountry(country)
            }

            RC_SPECIALIZATION -> {
                val specializations = data!!.getSerializableExtra(EXTRA_DATA) as ArrayList<Specialization>
                mViewModel.setSpecializations(specializations)
            }

            RC_LANGUAGE -> {
                val languages = data!!.getSerializableExtra(EXTRA_DATA) as ArrayList<Language>
                mViewModel.setLanguages(languages)
            }
            RC_TIMING -> {
                val timimg = data!!.getStringArrayExtra(EXTRA_DATA) as String
                mViewModel.setTiming(timimg)
            }
        }

        mBinding.validate = true
    }

    override fun onDestroyView() {
        mDisposable?.dispose()
        super.onDestroyView()
    }

    private companion object {
        const val RC_SPECIALIZATION = 10
        const val RC_COUNTRY = 20
        const val RC_LANGUAGE = 30
        const val RC_TIMING = 40
    }
}