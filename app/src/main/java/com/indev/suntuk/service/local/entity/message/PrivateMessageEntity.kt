package com.indev.suntuk.service.local.entity.message

import com.indev.suntuk.service.api.response.PrivateMessageResponse
import com.indev.suntuk.service.local.entity.Message
import com.indev.suntuk.service.local.entity.fromResponse
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type PrivateMessage struct {
	ID      primitive.ObjectID   `json:"id,omitempty" bson:"_id,omitempty"`
	ChatID  string               `json:"chat_id" bson:"chat_id"`
	Users   []primitive.ObjectID `json:"users" bson:"users"`
	Sender  primitive.ObjectID   `json:"sender" bson:"sender"`
	Message *Message             `json:"message" bson:"message"`
	Status  PrivateMessageStatus `json:"status" bson:"status"`
	Log     *DataLog             `json:"log" bson:"log"`
}
 */
class PrivateMessageEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var chatId: String = ""
    var users: RealmList<String> = realmListOf()
    var sender: String = ""
    var message: Message? = null
    var status: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun PrivateMessageEntity.Companion.fromResponse(response: PrivateMessageResponse): PrivateMessageEntity {
    val entity = PrivateMessageEntity()
    entity.id = response.id
    entity.chatId = response.chatID
    entity.users = response.users.toRealmList()
    entity.sender = response.sender
    entity.message = Message.fromResponse(response.message)
    entity.status = response.status
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}