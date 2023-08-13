package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

data class Res<T: Any>(
    val code: Int,
    @SerializedName("error_message") val errorMessage: String?,
    val data: T? = null
)

typealias ResOK = Res<String>