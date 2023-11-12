package com.indev.suntuk.service.local.entity.stuk

import com.indev.suntuk.service.api.response.StukReactionResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type StukReaction struct {
	ID         primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID     primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	UserID     primitive.ObjectID `json:"user_id" bson:"user_id"`
	ReactionID primitive.ObjectID `json:"reaction_id" bson:"reaction_id"`
	Key        string             `json:"key" bson:"key"`
	ThumbKey   string             `json:"thumb_key" bson:"thumb_key"`
	Log        *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class StukReactionEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var stukId: String = ""
    var userId: String = ""
    var reactionId: String = ""
    var key: String = ""
    var thumbKey: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun StukReactionEntity.Companion.fromResponse(stukReactionResponse: StukReactionResponse): StukReactionEntity {
    val stukReactionEntity = StukReactionEntity()
    stukReactionEntity.id = stukReactionResponse.id
    stukReactionEntity.stukId = stukReactionResponse.stukID
    stukReactionEntity.userId = stukReactionResponse.userID
    stukReactionEntity.reactionId = stukReactionResponse.reactionID
    stukReactionEntity.key = stukReactionResponse.key
    stukReactionEntity.thumbKey = stukReactionResponse.thumbKey
    stukReactionEntity.createdAt = stukReactionResponse.log.createdAt
    stukReactionEntity.updatedAt = stukReactionResponse.log.updatedAt
    return stukReactionEntity
}