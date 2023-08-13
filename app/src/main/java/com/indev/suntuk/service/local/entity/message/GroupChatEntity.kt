package com.indev.suntuk.service.local.entity.message

import com.indev.suntuk.service.api.response.GroupChatResponse
import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.fromResponse
import io.realm.kotlin.types.RealmObject

/*
type GroupChat struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Title       string             `json:"title" bson:"title"`
	Description string             `json:"description" bson:"description"`
	Profile     string             `json:"profile" bson:"profile"`
	Thumb       string             `json:"thumb" bson:"thumb"`
	Member      int64              `json:"member" bson:"member"`
	IsPublic    bool               `json:"is_public" bson:"is_public"`
	LastMessage *Message           `json:"last_message" bson:"last_message"`
	IsAvailable bool               `json:"is_available" bson:"is_available"`
	Log         *DataLog           `json:"log" bson:"log"`
}
 */
class GroupChatEntity: RealmObject {
    var id: String = ""
    var userId: String = ""
    var title: String = ""
    var description: String = ""
    var profile: String = ""
    var thumb: String = ""
    var member: Long = 0
    var isPublic: Boolean = false
    var lastMessage: Message? = null
    var isAvailable: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun GroupChatEntity.Companion.fromResponse(response: GroupChatResponse): GroupChatEntity {
    val entity = GroupChatEntity()
    entity.id = response.id
    entity.userId = response.userID
    entity.title = response.title
    entity.description = response.description
    entity.profile = response.profile
    entity.thumb = response.thumb
    entity.member = response.member
    entity.isPublic = response.isPublic
    entity.lastMessage = Message.fromResponse(response.lastMessage)
    entity.isAvailable = response.isAvailable
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}