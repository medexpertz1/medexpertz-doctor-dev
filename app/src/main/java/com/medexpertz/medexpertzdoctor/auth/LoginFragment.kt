package com.medexpertz.medexpertzdoctor.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.medexpertz.medexpertzdoctor.LoginFragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 13 Apr 2018 at 11:25 AM
 */
class LoginFragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: LoginFragmentBinding
    private var mDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mBinding.handler = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = ViewModelProviders.of(activity!!, mFactory)[AuthViewModel::class.java]
        mViewModel.auth.observe(this, Observer { mBinding.auth = it })
        var fcm_token = (ClsGeneral.getPreferences(activity, "fcm_token"))
        mViewModel.setFcmToken(fcm_token)
        mViewModel.setLoginFcmToken(fcm_token)

        val email = RxTextView.afterTextChangeEvents(mBinding.emailET).skipInitialValue()
        val password = RxTextView.afterTextChangeEvents(mBinding.passwordET).skipInitialValue()
        mDisposable = Observable.combineLatest(listOf(email, password ), { mBinding.validate = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()

        forgotTV.setOnClickListener {

        startActivity(Intent(activity, ForgotPassword::class.java))
        }
    }

    fun onSignIn() {
        mViewModel.signIn().observe(this, Observer { mBinding.state = it })
    }

    fun onRegister() {
        mViewModel.navigateToRegister()
    }

    override fun onDestroyView() {
        mDisposable?.dispose()
        super.onDestroyView()
    }
}