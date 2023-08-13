package com.indev.suntuk.service.local.entity.reply

import com.indev.suntuk.service.api.response.ReplyLikeResponse
import io.realm.kotlin.types.RealmObject

/*
type ReplyLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	ReplyID   primitive.ObjectID `json:"reply_id" bson:"reply_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class ReplyLikeEntity: RealmObject {
    var id: String = ""
    var replyId: String = ""
    var userId: String = ""
    var available: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun ReplyLikeEntity.Companion.fromResponse(response: ReplyLikeResponse): ReplyLikeEntity {
    val entity = ReplyLikeEntity()
    entity.id = response.id
    entity.replyId = response.replyID
    entity.userId = response.userID
    entity.available = response.available
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}