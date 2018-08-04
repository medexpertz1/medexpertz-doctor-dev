package com.medexpertz.medexpertzdoctor.etc.model

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 08 Jan 2018 at 3:36 PM
 */
class ApiResponse<out D>(val status: Int, val message: String?, val data: D)