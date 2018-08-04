package com.medexpertz.medexpertzdoctor.auth

import java.io.Serializable

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 09 Apr 2018 at 10:45 AM
 */
data class Specialization(
        val id: Int,
        val specialization: String,
        val descriptions: String?,
        val imagePath: String?,
        var isSelected: Boolean = false
) : Serializable {
    fun invertSelection() {
        isSelected = !isSelected
    }
}