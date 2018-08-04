package com.medexpertz.medexpertzdoctor.auth

import android.text.TextUtils
import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.medexpertz.medexpertzdoctor.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 Apr 2018 at 1:51 PM
 */
data class Profile(
        var name: String? = null,
        var email: String? = null,
        var profilePic: String? = null,
        var education: String? = null,
        var experience: String? = null,
        var consultationFee: String? = null,
        var password: String? = null,

        var gender: String? = null,
        var licenceDocument: String? = null,
        var specialization: List<Int>? = null,
        var specializationName: String? = null,
        var knownLanguages: List<Int>? = null,
        var languagesName: String? = null,
        var countryId: Int = -1,
        var dialCode: String? = "0",
        var countryName: String?= null,
        var fcm_token: String?= null,
        var timing: String?= null,
        var registrationId: String?= null,

        @SerializedName("mobile_no")
        var mobile: String? = null,

        var token: String? = null
) {
    fun isNameValid() = !TextUtils.isEmpty(name)

    fun isMobileValid() = mobile != null && Patterns.PHONE.matcher(mobile).matches()

    fun isPasswordValid() = !TextUtils.isEmpty(password)

    fun isGenderValid() = !TextUtils.isEmpty(gender)

    fun isEmailValid(): Boolean {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isForm1Valid(): Boolean {
        return isNameValid() && isMobileValid() && isEmailValid() && isPasswordValid() && isGenderValid()
    }

    private fun isExperienceValid(): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        return try {
            sdf.parse(experience)
            true
        } catch (e: ParseException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }

    fun setGender(id: Int) {
        gender = if (id == R.id.maleRB) "Male" else "Female"
    }

    private fun isEducationValid() = !TextUtils.isEmpty(education)

    private fun isFeeValid(): Boolean {
        return !TextUtils.isEmpty(consultationFee) && consultationFee!!.toDoubleOrNull() != null
    }

    private fun isSpecializationValid() = specialization != null && !specialization!!.isEmpty()

    private fun isLanguageValid() = knownLanguages != null && !knownLanguages!!.isEmpty()

    fun isForm2Valid(): Boolean {
        return isExperienceValid() && isEducationValid() && isFeeValid() && isSpecializationValid() && countryId != -1
                && isLanguageValid()
    }

    fun languageList(language: ArrayList<Language>){

        for (i in 0 until language.size) {
            if (TextUtils.isEmpty(languagesName))
                languagesName = language[i].languageName
            else languagesName = languagesName+","+ language[i].languageName
        }

    }
}