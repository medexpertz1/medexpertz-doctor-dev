package com.medexpertz.medexpertzdoctor.etc.di

import android.app.Application
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 07 May 2018 at 9:19 AM
 */
@Module
class CometChatModule {
    @Provides
    @Singleton
    fun cometProvider(app: Application): CometChat {
        return CometChat.getInstance(app)
    }
}