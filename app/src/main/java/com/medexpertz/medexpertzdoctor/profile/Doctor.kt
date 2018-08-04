package com.medexpertz.medexpertzdoctor.profile

import com.google.gson.annotations.SerializedName
import com.medexpertz.medexpertzdoctor.auth.Language

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 04 May 2018 at 3:30 PM
 */
data class Doctor(
        var name: String? = null,
        var email: String? = null,
        var profilePic: String? = null,
        var education: String? = null,
        var experience: String? = null,
        var consultationFees: String? = null,

        var gender: String? = null,
        var licenceDocument: String? = null,
        var specialization: List<Spec>? = null,
        var knownLanguages: List<Language>? = null,
        var doctorTiming: String? = null,

        @SerializedName("mobile_no")
        var mobile: String? = null
) {
    fun languages() = knownLanguages?.joinToString { it.languageName }

    fun displaySpecializations() = specialization?.joinToString { it.specTitle }
}

data class Spec(val specId: Int, val specTitle: String)