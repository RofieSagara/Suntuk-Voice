package com.indev.suntuk.utils

import com.indev.suntuk.service.api.response.Res
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T: Any> (suspend () -> Res<T>).asResultFlow(): Flow<Result<T>> {
    return this@asResultFlow.asFlow()
        .catch {
            emit(Res(0, it.message, null))
        }.map {
            if (it.code == 200 && it.data != null) {
                Result.success(it.data)
            } else {
                Result.failure(Throwable("[${it.code}]${it.errorMessage}"))
            }
        }
}

fun <T: Any> (suspend () -> Res<T>).asResultFlowThrow(): Flow<T> {
    return this@asResultFlowThrow.asResultFlow()
        .map { result -> result.getOrThrow() }
}
