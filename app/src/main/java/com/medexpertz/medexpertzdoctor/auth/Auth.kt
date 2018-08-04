package com.medexpertz.medexpertzdoctor.auth

import android.util.Patterns

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 Apr 2018 at 12:04 PM
 */
data class Auth(
        var email: String? = null,
        var password: String? = null,
        var fcm_token: String? = null
) {
    fun isEmailValid(): Boolean {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(): Boolean {
        return password != null && password?.length!! > 5
    }

    fun isValid(): Boolean {
        return isEmailValid() && isPasswordValid()
    }
}