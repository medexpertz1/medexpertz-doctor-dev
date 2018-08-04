package com.medexpertz.medexpertzdoctor.auth

import com.google.gson.annotations.SerializedName

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 26 Apr 2018 at 11:34 AM
 */

data class ProfileResponse(
        var name: String? = null,
        var email: String? = null,
        var profilePic: String? = null,
        var education: String? = null,
        var experience: String? = null,
        var consultationFee: String? = null,
        var password: String? = null,

        var gender: String? = null,
        var licenceDocument: String? = null,
        var countryId: Int = -1,

        @SerializedName("mobile_no")
        var mobile: String? = null,

        var token: String? = null,
        val cometId: String
)