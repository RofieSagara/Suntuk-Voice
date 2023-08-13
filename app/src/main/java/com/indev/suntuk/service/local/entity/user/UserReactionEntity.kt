package com.indev.suntuk.service.local.entity.user

import com.indev.suntuk.service.api.response.UserReactionResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type UserReaction struct {
	ID         primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID     primitive.ObjectID `json:"user_id" bson:"user_id"`
	ReactionID primitive.ObjectID `json:"reaction_id" bson:"reaction_id"`
	Key        string             `json:"key" bson:"key"`
	ThumbKey   string             `json:"thumb_key" bson:"thumb_key"`
	Amount     int64              `json:"amount" bson:"amount"`
	Log        *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class UserReactionEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var userId: String = ""
    var reactionId: String = ""
    var key: String = ""
    var thumbKey: String = ""
    var amount: Int = 0
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun UserReactionEntity.Companion.fromResponse(userReactionResponse: UserReactionResponse): UserReactionEntity {
    val userReactionEntity = UserReactionEntity()
    userReactionEntity.id = userReactionResponse.id
    userReactionEntity.userId = userReactionResponse.userID
    userReactionEntity.reactionId = userReactionResponse.reactionID
    userReactionEntity.key = userReactionResponse.key
    userReactionEntity.thumbKey = userReactionResponse.thumbKey
    userReactionEntity.amount = userReactionResponse.amount
    userReactionEntity.createdAt = userReactionResponse.log.createdAt
    userReactionEntity.updatedAt = userReactionResponse.log.updatedAt
    return userReactionEntity
}