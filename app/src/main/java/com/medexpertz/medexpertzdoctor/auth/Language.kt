package com.medexpertz.medexpertzdoctor.auth

import java.io.Serializable

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 17 Apr 2018 at 7:49 PM
 */
data class Language(
        val languageId: Int,
        val languageName: String,
        var isSelected: Boolean = false
) : Serializable {
    fun invertSelection() {
        isSelected = !isSelected
    }
}