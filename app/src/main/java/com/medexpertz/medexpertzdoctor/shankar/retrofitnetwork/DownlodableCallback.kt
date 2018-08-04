package com.medexpertz.medexpertzdoctor.shankar.retrofitnetwork

/**
 * Created by akshay on 12/8/17.
 */

interface DownlodableCallback<T> {


    fun onSuccess(result: T)

    fun onFailure(error: String)
    fun onUnauthorized(errorNumber: Int)
}
