package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.Content
import com.indev.suntuk.service.local.entity.StukSettings
import com.indev.suntuk.service.local.entity.stuk.StukEntity

data class Stuk(
    val id: String = "",
    val userId: String = "",
    val parentId: String? = null,
    val threadId: String = "",
    val nickname: String = "",
    val profile: String = "",
    val isAnonymous: Boolean = false,
    val content: Content? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    val replies: Int = 0,
    val reactions: Int = 0,
    val settings: StukSettings? = null,
    val available: Boolean = true,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun Stuk.Companion.fromEntity(entity: StukEntity): Stuk {
    return Stuk(
        id = entity.id,
        userId = entity.userId,
        parentId = entity.parentId,
        threadId = entity.threadId,
        nickname = entity.nickname,
        profile = entity.profile,
        isAnonymous = entity.isAnonymous,
        content = entity.content,
        likes = entity.likes,
        comments = entity.comments,
        replies = entity.replies,
        reactions = entity.reactions,
        settings = entity.settings,
        available = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}