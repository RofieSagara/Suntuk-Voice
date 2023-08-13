package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.Content
import com.indev.suntuk.service.local.entity.comment.CommentEntity
import com.indev.suntuk.service.local.entity.fromResponse

data class Comment(
    val id: String,
    val stukID: String,
    val userID: String,
    val nickname: String,
    val profile: String,
    val content: Content?,
    val isAnonymous: Boolean,
    val likes: Long,
    val replies: Long,
    val reactions: Long,
    val available: Boolean,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun Comment.Companion.fromEntity(entity: CommentEntity): Comment {
    return Comment(
        id = entity.id,
        stukID = entity.stukId,
        userID = entity.userId,
        nickname = entity.nickname,
        profile = entity.profile,
        content = entity.content,
        isAnonymous = entity.isAnonymous,
        likes = entity.likes,
        replies = entity.replies,
        reactions = entity.reactions,
        available = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}