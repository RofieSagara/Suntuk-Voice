package com.indev.suntuk.service.local.entity.user

import com.indev.suntuk.service.api.response.UserBlockResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type UserBlock struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	TargetID primitive.ObjectID `json:"target_id" bson:"target_id"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class UserBlockEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var userId: String = ""
    var targetId: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun UserBlockEntity.Companion.fromResponse(userBlockResponse: UserBlockResponse): UserBlockEntity {
    val userBlockEntity = UserBlockEntity()
    userBlockEntity.id = userBlockResponse.id
    userBlockEntity.userId = userBlockResponse.userID
    userBlockEntity.targetId = userBlockResponse.targetID
    userBlockEntity.createdAt = userBlockResponse.log.createdAt
    userBlockEntity.updatedAt = userBlockResponse.log.updatedAt
    return userBlockEntity
}