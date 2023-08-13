package com.indev.suntuk.service.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.indev.suntuk.service.api.dto.MessageServiceDeleteGroupResponse
import com.indev.suntuk.service.api.dto.MessageServiceFetchGroupChatResponse
import com.indev.suntuk.service.api.dto.MessageServiceInviteGroupResponse
import com.indev.suntuk.service.api.dto.MessageServiceJoinGroupResponse
import com.indev.suntuk.service.api.dto.MessageServiceLeaveGroupResponse
import com.indev.suntuk.service.api.dto.MessageServiceManipulateGroupMemberResponse
import com.indev.suntuk.service.api.dto.MessageServiceSendTextGroupMessageResponse
import com.indev.suntuk.service.api.dto.MessageServiceSendTextMessageResponse
import com.indev.suntuk.service.api.dto.MessageServiceUpdateProfileNicknameGroupMemberResponse
import com.indev.suntuk.service.api.response.Res
import com.indev.suntuk.service.api.response.ResOK
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File

interface MessageAPI {
    suspend fun sendTextMessage(targetID: String, chatID: String, contentText: String): Res<MessageServiceSendTextMessageResponse>
    suspend fun sendImageMessage(targetID: String, chatID: String,  contentText: String, contentImage: String, file: File)
    suspend fun sendVoiceMessage(targetID: String, chatID: String, contentText: String, contentVoice: String, file: File)
    suspend fun fetchGroupChat(): Res<MessageServiceFetchGroupChatResponse>
    suspend fun createGroupChat(title: String, description: String, isPublic: Boolean, file: File)
    suspend fun updatedGroupChat(groupID: String, title: String, description: String, isPublic: Boolean, file: File?)
    suspend fun deleteGroup(groupID: String): Res<MessageServiceDeleteGroupResponse>
    suspend fun sendGroupTextMessage(groupID: String, parentID: String, recipients: List<String>, contentText: String): Res<MessageServiceSendTextGroupMessageResponse>
    suspend fun sendGroupImageMessage(groupID: String, parentID: String, recipients: List<String>, contentText: String, contentImage: File)
    suspend fun sendGroupVoiceMessage(groupID: String, parentID: String, recipients: List<String>, contentText: String, contentVoice: File)
    suspend fun leaveGroup(groupID: String): Res<MessageServiceLeaveGroupResponse>
    suspend fun inviteGroup(groupID: String, targetID: String): Res<MessageServiceInviteGroupResponse>
    suspend fun joinGroup(groupID: String): Res<MessageServiceJoinGroupResponse>
    suspend fun manipulateGroupMember(groupID: String, targetID: String, isAvailable: Boolean): Res<MessageServiceManipulateGroupMemberResponse>
    suspend fun updateProfileGroupMember(groupID: String, targetID: String, profile: String, nickname: String): Res<MessageServiceUpdateProfileNicknameGroupMemberResponse>
}

class IMessageAPI(private val client: HttpClient): MessageAPI {
    override suspend fun sendTextMessage(
        targetID: String,
        chatID: String,
        contentText: String
    ): Res<MessageServiceSendTextMessageResponse> {
        return client.post("/message/text") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("target_id", targetID)
                addProperty("chat_id", chatID)
                addProperty("content_text", contentText)
            })
        }.body()
    }

    override suspend fun sendImageMessage(
        targetID: String,
        chatID: String,
        contentText: String,
        contentImage: String,
        file: File
    ) {
        client.post("/message/image") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("target_id", targetID)
                    append("chat_id", chatID)
                    append("content_text", contentText)
                    append("file", file.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "image/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun sendVoiceMessage(
        targetID: String,
        chatID: String,
        contentText: String,
        contentVoice: String,
        file: File
    ) {
        client.post("/message/voice") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("target_id", targetID)
                    append("chat_id", chatID)
                    append("content_text", contentText)
                    append("file", file.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "audio/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun fetchGroupChat(): Res<MessageServiceFetchGroupChatResponse> {
        return client.get("/message/group/fetch")
            .body()
    }

    override suspend fun createGroupChat(
        title: String,
        description: String,
        isPublic: Boolean,
        file: File
    ) {
        client.post("/message/group") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("title", title)
                    append("description", description)
                    append("is_public", isPublic)
                    append("file", file.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "image/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun updatedGroupChat(
        groupID: String,
        title: String,
        description: String,
        isPublic: Boolean,
        file: File?
    ) {
        client.put("/message/group") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("group_id", groupID)
                    append("title", title)
                    append("description", description)
                    append("is_public", isPublic)
                    if (file != null) {
                        append("file", file.readBytes(), Headers.build {
                            this.append(HttpHeaders.ContentType, "image/*")
                            this.append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                        })
                    }
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun deleteGroup(groupID: String): Res<MessageServiceDeleteGroupResponse> {
        return client.delete("/message/group") {
            parameter("group_id", groupID)
        }.body()
    }

    override suspend fun sendGroupTextMessage(
        groupID: String,
        parentID: String,
        recipients: List<String>,
        contentText: String
    ): Res<MessageServiceSendTextGroupMessageResponse> {
        return client.post("/message/group/text") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
                add("recipients", JsonArray().apply { recipients.forEach { add(it) } })
                addProperty("content_text", contentText)
                addProperty("parent_message_id", parentID)
            })
        }.body()
    }

    override suspend fun sendGroupImageMessage(
        groupID: String,
        parentID: String,
        recipients: List<String>,
        contentText: String,
        contentImage: File
    ) {
        client.post("/message/group/image") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("group_id", groupID)
                    append("parent_id", parentID)
                    append("recipients", recipients.joinToString(","))
                    append("content_text", contentText)
                    append("file", contentImage.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "image/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${contentImage.name}")
                    })
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun sendGroupVoiceMessage(
        groupID: String,
        parentID: String,
        recipients: List<String>,
        contentText: String,
        contentVoice: File
    ) {
        client.post("/message/group/voice") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("group_id", groupID)
                    append("parent_id", parentID)
                    append("recipients", recipients.joinToString(","))
                    append("content_text", contentText)
                    append("file", contentVoice.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "audio/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${contentVoice.name}")
                    })
                }
            ))
        }.body<ResOK>()
    }

    override suspend fun leaveGroup(groupID: String): Res<MessageServiceLeaveGroupResponse> {
        return client.post("/message/group/leave") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
            })
        }.body()
    }

    override suspend fun inviteGroup(
        groupID: String,
        targetID: String
    ): Res<MessageServiceInviteGroupResponse> {
        return client.post("/message/group/invite") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
                addProperty("target_user_id", targetID)
            })
        }.body()
    }

    override suspend fun joinGroup(groupID: String): Res<MessageServiceJoinGroupResponse> {
        return client.post("/message/group/join") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
            })
        }.body()
    }

    override suspend fun manipulateGroupMember(
        groupID: String,
        targetID: String,
        isAvailable: Boolean
    ): Res<MessageServiceManipulateGroupMemberResponse> {
        return client.post("/message/group/manipulate") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
                addProperty("target_id", targetID)
                addProperty("is_available", isAvailable)
            })
        }.body()
    }

    override suspend fun updateProfileGroupMember(
        groupID: String,
        targetID: String,
        profile: String,
        nickname: String
    ): Res<MessageServiceUpdateProfileNicknameGroupMemberResponse> {
        return client.post("/message/group/update-profile-nickname") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("group_id", groupID)
                addProperty("profile", profile)
                addProperty("nickname", nickname)
            })
        }.body()
    }

}