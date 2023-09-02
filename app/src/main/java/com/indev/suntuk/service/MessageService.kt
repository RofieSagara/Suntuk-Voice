package com.indev.suntuk.service

import com.indev.suntuk.service.model.ChatUI
import com.indev.suntuk.service.model.GroupMessageUI
import com.indev.suntuk.service.model.PrivateChatMessageUI
import kotlinx.coroutines.flow.Flow

interface MessageService {

    fun sendTextPrivateMessage(targetID: String, text: String): Flow<Unit>
    fun sendImagePrivateMessage(targetID: String, text: String, image: String): Flow<Unit>
    fun sendVoicePrivateMessage(targetID: String, text: String, voice: String): Flow<Unit>

    fun updatePrivateChatStatus(chatID: String): Flow<Unit>
    fun readPrivateChat(chatID: String): Flow<Unit>

    fun liveChat(): Flow<List<ChatUI>>
    fun livePrivateChatMessage(chatID: String, count: Int): Flow<List<PrivateChatMessageUI>>
    fun liveGroupMessage(groupID: String, count: Int): Flow<List<GroupMessageUI>>
    fun loadGroupMessageNewest(groupID: String): Flow<List<GroupMessageUI>>
    fun loadGroupMessageOldest(groupID: String): Flow<List<GroupMessageUI>>
}