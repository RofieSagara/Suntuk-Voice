package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.reply.ReplyLikeEntity

data class ReplyLike(
    val id: String,
    val replyID: String,
    val userID: String,
    val available: Boolean,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun ReplyLike.Companion.fromEntity(entity: ReplyLikeEntity): ReplyLike {
    return ReplyLike(
        id = entity.id,
        replyID = entity.replyId,
        userID = entity.userId,
        available = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}