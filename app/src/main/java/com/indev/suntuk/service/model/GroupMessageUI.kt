package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.ParentMessage
import com.indev.suntuk.service.local.entity.message.GroupMessageEntity

data class GroupMessageUI(
    var id: String,
    var groupId: String,
    var userId: String,
    var userProfile: String,
    var userNickname: String,
    var parentMessage: ParentMessage?,
    var message: Message?,
    var recipients: List<String>,
    var isPrivate: Boolean,
    var status: String,
    var createdAt: Long,
    var updatedAt: Long,
) {
    companion object {}
}

fun GroupMessageUI.Companion.fromEntity(entity: GroupMessageEntity): GroupMessageUI {
    return GroupMessageUI(
        id = entity.id,
        groupId = entity.groupId,
        userId = entity.userId,
        userProfile = entity.userProfile,
        userNickname = entity.userNickname,
        parentMessage = entity.parentMessage,
        message = entity.message,
        recipients = entity.recipients,
        isPrivate = entity.isPrivate,
        status = entity.status,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}