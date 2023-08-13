package com.indev.suntuk.service.local.entity.stuk

import com.indev.suntuk.service.api.response.StukDeletedResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type StukDeleted struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	DeleteBy primitive.ObjectID `json:"delete_by" bson:"delete_by"`
	Reason   string             `json:"reason" bson:"reason"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class StukDeletedEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var userId: String = ""
    var deleteBy: String = ""
    var reason: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun StukDeletedEntity.Companion.fromResponse(stukDeletedResponse: StukDeletedResponse): StukDeletedEntity {
    val stukDeletedEntity = StukDeletedEntity()
    stukDeletedEntity.id = stukDeletedResponse.id
    stukDeletedEntity.userId = stukDeletedResponse.userID
    stukDeletedEntity.deleteBy = stukDeletedResponse.deleteBy
    stukDeletedEntity.reason = stukDeletedResponse.reason
    stukDeletedEntity.createdAt = stukDeletedResponse.log.createdAt
    stukDeletedEntity.updatedAt = stukDeletedResponse.log.updatedAt
    return stukDeletedEntity
}