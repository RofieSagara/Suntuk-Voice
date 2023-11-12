package com.indev.suntuk.service.local.entity.message

import com.indev.suntuk.service.api.response.GroupChatMemberResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type GroupChatMember struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	GroupID     primitive.ObjectID `json:"group_id" bson:"group_id"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Profile     string             `json:"profile" bson:"profile"`
	Nickname    string             `json:"nickname" bson:"nickname"`
	IsAvailable bool               `json:"is_available" bson:"is_available"`
	IsBot       bool               `json:"is_bot" bson:"is_bot"`
	Log         *DataLog           `json:"log" bson:"log"`
}
 */
class GroupChatMemberEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var groupId: String = ""
    var userId: String = ""
    var profile: String = ""
    var nickname: String = ""
    var isAvailable: Boolean = false
    var isBot: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun GroupChatMemberEntity.Companion.fromResponse(response: GroupChatMemberResponse): GroupChatMemberEntity {
    val entity = GroupChatMemberEntity()
    entity.id = response.id
    entity.groupId = response.groupID
    entity.userId = response.userID
    entity.profile = response.profile
    entity.nickname = response.nickname
    entity.isAvailable = response.isAvailable
    entity.isBot = response.isBot
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}