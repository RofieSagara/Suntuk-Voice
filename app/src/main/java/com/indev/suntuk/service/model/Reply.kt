package com.indev.suntuk.service.model

import com.indev.suntuk.service.api.response.ContentResponse
import com.indev.suntuk.service.api.response.DataLogResponse
import com.indev.suntuk.service.local.entity.Content
import com.indev.suntuk.service.local.entity.reply.ReplyEntity

data class Reply(
    val id: String,
    val stukID: String,
    val commentID: String,
    val userID: String,
    val nickname: String,
    val profile: String,
    val content: Content?,
    val isAnonymous: Boolean,
    val likes: Long,
    val reactions: Long,
    val available: Boolean,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun Reply.Companion.fromEntity(entity: ReplyEntity): Reply {
    return Reply(
        id = entity.id,
        stukID = entity.stukId,
        commentID = entity.commentId,
        userID = entity.userId,
        nickname = entity.nickname,
        profile = entity.profile,
        content = entity.content,
        isAnonymous = entity.isAnonymous,
        likes = entity.likes,
        reactions = entity.reactions,
        available = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}