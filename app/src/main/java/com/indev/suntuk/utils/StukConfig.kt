package com.indev.suntuk.utils

import kotlinx.coroutines.flow.Flow

data class StukConfig(
    val isCommentActive: Boolean,
    val isReplyActive: Boolean,
    val isChatActive: Boolean
)

fun liveStukConfig(): Flow<StukConfig> {
    TODO("not implemented")
}