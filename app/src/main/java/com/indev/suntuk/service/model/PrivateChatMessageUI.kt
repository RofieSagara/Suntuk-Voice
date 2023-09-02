package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.message.PrivateMessageEntity

data class PrivateChatMessageUI(
    var id: String,
    var chatId: String,
    var users: List<String>,
    var sender: String,
    var message: Message?,
    var status: String,
    var createdAt: Long,
    var updatedAt: Long,
) {
    companion object {}
}

fun PrivateChatMessageUI.Companion.fromEntity(entity: PrivateMessageEntity): PrivateChatMessageUI {
    return PrivateChatMessageUI(
        id = entity.id,
        chatId = entity.chatId,
        users = entity.users,
        sender = entity.sender,
        message = entity.message,
        status = entity.status,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}