package com.indev.suntuk.service.local.entity.stuk

import com.indev.suntuk.service.api.response.StukLikeResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type StukLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID    primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class StukLikeEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var stukId: String = ""
    var userId: String = ""
    var available: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun StukLikeEntity.Companion.fromResponse(stukLikeResponse: StukLikeResponse): StukLikeEntity {
    val stukLikeEntity = StukLikeEntity()
    stukLikeEntity.id = stukLikeResponse.id
    stukLikeEntity.stukId = stukLikeResponse.stukID
    stukLikeEntity.userId = stukLikeResponse.userID
    stukLikeEntity.available = stukLikeResponse.available
    stukLikeEntity.createdAt = stukLikeResponse.log.createdAt
    stukLikeEntity.updatedAt = stukLikeResponse.log.updatedAt
    return stukLikeEntity
}