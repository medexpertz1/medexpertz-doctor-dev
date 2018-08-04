package com.medexpertz.medexpertzdoctor.auth

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 24 Nov 2017 at 5:14 PM
 */
enum class AuthState {
    INIT, REGISTER, AUTHENTICATED, LOADING, REQUEST_FAILED, PROFILE_2, DUPLICATE, INCORRECT_LOGIN,

    COMET_LOGIN_FAILED
}