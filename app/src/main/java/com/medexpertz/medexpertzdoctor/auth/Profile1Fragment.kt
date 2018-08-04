package com.medexpertz.medexpertzdoctor.auth

import android.Manifest.permission.*
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.jakewharton.rxbinding2.widget.RxTextView
import com.medexpertz.medexpertzdoctor.Profile1FragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.etc.common.ImageFilePath
import com.medexpertz.medexpertzdoctor.shankar.common.ClsGeneral
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.io.File
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 13 Apr 2018 at 12:34 PM
 */
class Profile1Fragment : BaseFragment() {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: AuthViewModel
    private lateinit var mBinding: Profile1FragmentBinding
    private var mDisposable: Disposable? = null
    private lateinit var mTransferUtil: TransferUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTransferUtil = TransferUtility.builder()
                .context(context!!.applicationContext)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(AmazonS3Client(AWSMobileClient.getInstance().credentialsProvider))
                .build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_1, container, false)
        mBinding.handler = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = ViewModelProviders.of(activity!!, mFactory)[AuthViewModel::class.java]
        mViewModel.profile.observe(this, Observer { mBinding.profile = it })

        mViewModel.setFcmToken(ClsGeneral.getPreferences(activity, "fcm_token"))

        val name = RxTextView.afterTextChangeEvents(mBinding.nameET).skipInitialValue()
        val email = RxTextView.afterTextChangeEvents(mBinding.emailET).skipInitialValue()
        val mobile = RxTextView.afterTextChangeEvents(mBinding.mobileET).skipInitialValue()
        val password = RxTextView.afterTextChangeEvents(mBinding.passwordET).skipInitialValue()
        mDisposable = Observable.combineLatest(listOf(email, name, mobile, password), { mBinding.validate = true })
                .doOnError { Timber.e(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun onAvatar() {
        if (ActivityCompat.checkSelfPermission(context!!, PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context!!, PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {
            launchImageIntent()
        } else {
            requestPermissions(PERMISSIONS, RC_STORAGE)
        }
    }

    fun setGender(id: Int) {
        mBinding.profile?.setGender(id)
        mBinding.validate = true
    }

    fun onNext() {
        mViewModel.profile1Completed()
    }

    private fun launchImageIntent() {
        val pickIntent = Intent()
        pickIntent.type = "image/*"
        pickIntent.action = Intent.ACTION_GET_CONTENT
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickTitle = "Take or select a photo"
        val chooserIntent = Intent.createChooser(pickIntent, pickTitle)
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent))
        startActivityForResult(chooserIntent, RC_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_AVATAR && resultCode == Activity.RESULT_OK) {
            when {
                data?.data != null -> {
                    val uri = data.data
                    val path = ImageFilePath.getPath(context!!, uri)
                    Timber.d("Gallery file path: %s", path)
                    uploadImageToS3(path)
                }
                data?.extras?.get("data") is Bitmap -> {
                    val bitmap = data.extras.get("data") as Bitmap
                    val uri = ImageFilePath.getImageUri(context!!, bitmap)
                    val path = ImageFilePath.getPath(context!!, uri)
                    Timber.d("Camera file path: %s", path)
                    uploadImageToS3(path)
                }
                else -> Toast.makeText(context!!, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToS3(path: String) {
        val file = File(path)
        val key = "avatar/${System.nanoTime()}.jpg"
        val observer = mTransferUtil.upload(key, file, CannedAccessControlList.PublicRead)
        observer.setTransferListener(object : TransferListener {
            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val percentDoneF = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                val percentDone = percentDoneF.toInt()
                Timber.d("ID:$id bytesCurrent: $bytesCurrent bytesTotal: $bytesTotal $percentDone%")
                mBinding.transferProgress = percentDoneF.toInt().toFloat()
            }

            override fun onStateChanged(id: Int, state: TransferState?) {
                Timber.d("State : %s", state?.toString())
                mBinding.transferState = state
                if (TransferState.COMPLETED == state) {
                    val url = URL_BUCKET + key
                    Timber.d(url)
                    mViewModel.setProfileImageUrl(url)
                }
            }

            override fun onError(id: Int, ex: Exception?) {
                Timber.e(ex)
                Toast.makeText(context!!, R.string.upload_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchImageIntent()
        } else {
            Toast.makeText(context!!, R.string.permission_denied, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        mDisposable?.dispose()
        super.onDestroyView()
    }

    companion object {
        private val PERMISSIONS = arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        private const val RC_AVATAR = 4
        private const val RC_STORAGE = 8
        private const val URL_BUCKET = "https://s3.amazonaws.com/medexpertz-userfiles-mobilehub-452052235/"
    }
}