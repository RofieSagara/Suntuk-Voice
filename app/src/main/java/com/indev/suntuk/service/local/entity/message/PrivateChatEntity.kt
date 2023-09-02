package com.indev.suntuk.service.local.entity.message

import io.realm.kotlin.types.RealmObject

class PrivateChatEntity: RealmObject {
    var id: String = ""
    var chatID: String = ""
    var targetId: String = ""
    var profile: String = ""
    var nickName: String = ""
    var lastMessageID: String = ""
    var lastMessage: String = ""
    var lastMessageTime: Long = 0
    var unread: Long = 0
    var createdAt: Long = 0
    var updatedAt: Long = 0
}