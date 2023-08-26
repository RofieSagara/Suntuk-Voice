package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.message.GroupChatEntity

data class GroupChat(
    var id: String,
    var userId: String,
    var title: String,
    var description: String,
    var profile: String,
    var thumb: String,
    var member: Long,
    var isPublic: Boolean,
    var lastMessage: Message?,
    var isAvailable: Boolean,
    var createdAt: Long,
    var updatedAt: Long
) {
    companion object {}
}

fun GroupChat.Companion.fromEntity(entity: GroupChatEntity): GroupChat {
    return GroupChat(
        id = entity.id,
        userId = entity.userId,
        title = entity.title,
        description = entity.description,
        profile = entity.profile,
        thumb = entity.thumb,
        member = entity.member,
        isPublic = entity.isPublic,
        lastMessage = entity.lastMessage,
        isAvailable = entity.isAvailable,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}