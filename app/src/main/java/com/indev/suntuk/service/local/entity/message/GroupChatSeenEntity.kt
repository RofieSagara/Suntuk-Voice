package com.indev.suntuk.service.local.entity.message

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class GroupChatSeenEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var lastMessageString: String = ""
    var lastMessageTime: Long = 0
    var lastMessageSeen: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0
}