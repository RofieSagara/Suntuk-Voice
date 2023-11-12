package com.indev.suntuk.service.local.entity.message

import com.indev.suntuk.service.api.response.GroupMessageResponse
import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.ParentMessage
import com.indev.suntuk.service.local.entity.fromResponse
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type GroupMessage struct {
	ID            primitive.ObjectID   `json:"id,omitempty" bson:"_id,omitempty"`
	GroupID       primitive.ObjectID   `json:"group_id" bson:"group_id"`
	UserID        primitive.ObjectID   `json:"user_id" bson:"user_id"`
	UserProfile   string               `json:"user_profile" bson:"user_profile"`
	UserNickname  string               `json:"user_nickname" bson:"user_nickname"`
	ParentMessage *ParentMessage       `json:"parent_message" bson:"parent_message"`
	Message       *Message             `json:"message" bson:"message"`
	Recipients    []primitive.ObjectID `json:"recipients" bson:"recipients"`
	IsPrivate     bool                 `json:"is_private" bson:"is_private"`
	Status        GroupMessageStatus   `json:"status" bson:"status"`
	Log           *DataLog             `json:"log" bson:"log"`
}
 */
class GroupMessageEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var groupId: String = ""
    var userId: String = ""
    var userProfile: String = ""
    var userNickname: String = ""
    var parentMessage: ParentMessage? = null
    var message: Message? = null
    var recipients: RealmList<String> = realmListOf()
    var isPrivate: Boolean = false
    var status: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun GroupMessageEntity.Companion.fromResponse(response: GroupMessageResponse): GroupMessageEntity {
    val entity = GroupMessageEntity()
    entity.id = response.id
    entity.groupId = response.groupID
    entity.userId = response.userID
    entity.userProfile = response.userProfile
    entity.userNickname = response.userNickname
    entity.parentMessage = ParentMessage.fromResponse(response.parentMessage)
    entity.message = Message.fromResponse(response.message)
    entity.recipients = response.recipients.toRealmList()
    entity.isPrivate = response.isPrivate
    entity.status = response.status
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}