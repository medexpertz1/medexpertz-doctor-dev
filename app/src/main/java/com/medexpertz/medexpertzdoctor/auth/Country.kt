package com.medexpertz.medexpertzdoctor.auth

import java.io.Serializable

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 06 Apr 2018 at 12:43 PM
 */
data class Country(
        val id: Int,
        val countryCode: String,
        val countryName: String,
        val dialCode: String
) : Serializable