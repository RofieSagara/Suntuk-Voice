package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.message.PrivateChatEntity

data class PrivateChat(
    var id: String,
    var targetId: String,
    var profile: String,
    var nickName: String,
    var lastMessageID: String,
    var lastMessage: String,
    var lastMessageTime: Long,
    var unread: Long,
    var createdAt: Long,
    var updatedAt: Long
) {
    companion object {}
}

fun PrivateChat.Companion.fromEntity(entity: PrivateChatEntity): PrivateChat {
    return PrivateChat(
        id = entity.id,
        targetId = entity.targetId,
        profile = entity.profile,
        nickName = entity.nickName,
        lastMessageID = entity.lastMessageID,
        lastMessage = entity.lastMessage,
        lastMessageTime = entity.lastMessageTime,
        unread = entity.unread,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}