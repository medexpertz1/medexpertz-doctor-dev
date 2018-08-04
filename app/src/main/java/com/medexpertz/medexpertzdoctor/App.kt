package com.medexpertz.medexpertzdoctor

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.inscripts.interfaces.Callbacks
import com.inscripts.orm.SugarContext
import com.medexpertz.medexpertzdoctor.etc.di.AppInjector
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 29 Nov 2017 at 10:50 AM
 */

class App : MultiDexApplication(), HasActivityInjector {
    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var cometChat: CometChat

    override fun onCreate() {
        super.onCreate()

        SugarContext.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Plant a production tree for crash analytics
        }

        AppInjector.init(this)
        val realmConfiguration = RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        cometChatInit()
    }

    private fun cometChatInit() {
        cometChat.initializeCometChat(SITE_URL, LICENCE_KEY, API_KEY, true, object : Callbacks {
            override fun successCallback(p0: JSONObject?) {
                Timber.e("Success: " + p0?.toString())
                /*cometChat.setStatus(StatusOption.AVAILABLE, object: Callbacks {
                    override fun successCallback(p0: JSONObject?) {
                        cometChat.setStatus(StatusOption.AVAILABLE, object: Callbacks {
                            override fun successCallback(p0: JSONObject?) {
                                Timber.e("Status success: %s", p0?.toString())
                            }

                            override fun failCallback(p0: JSONObject?) {
                                Timber.e("Status failed: %s", p0?.toString())
                            }
                        })
                    }

                    override fun failCallback(p0: JSONObject?) {
                    }
                })*/
            }

            override fun failCallback(p0: JSONObject?) {
                Timber.e("Failure: " + p0?.toString())
            }
        })
    }

    fun cometChatLogin(cometUserId: String) {
        cometChat.login(cometUserId, object : Callbacks {
            override fun successCallback(p0: JSONObject?) {
                Timber.e("CometChat: Logged in successfully with the id: $cometUserId\n${p0?.toString()}")
            }

            override fun failCallback(p0: JSONObject?) {
                Timber.e("CometChat: Logged in failed with the id: $cometUserId\n${p0?.toString()}")
            }
        })
    }

    override fun activityInjector() = injector

    companion object {
        const val SITE_URL = "localhost"
        const val LICENCE_KEY = "COMETCHAT-YMXPF-AQVXX-3MDSF-CB3NO"
        const val API_KEY = "50851xea1f66202b58273f6171e9e67250a34b"
    }
}