package com.indev.suntuk.service.model

data class ChatUI(
    var id: String,
    var targetId: String,
    var profile: String,
    var nickName: String,
    var lastMessageID: String,
    var lastMessage: String,
    var lastMessageTime: Long,
    var isTyping: Boolean,
    var chatType: String,
    var unread: Long,
    var createdAt: Long,
    var updatedAt: Long
)