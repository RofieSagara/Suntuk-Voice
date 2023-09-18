package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

data class Res<T: Any>(
    val code: Int,
    @SerializedName("error_message") val errorMessage: String?,
    val data: T? = null
)

typealias ResOK = Res<String>

class ResponseException(message: String?) : Throwable(message)

fun <T : Any> Res<T>.throwIfError() {
    if (code != 200) {
        throw ResponseException(errorMessage)
    }
}