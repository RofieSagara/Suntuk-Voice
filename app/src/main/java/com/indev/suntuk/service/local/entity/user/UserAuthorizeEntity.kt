package com.indev.suntuk.service.local.entity.user

import com.indev.suntuk.service.api.response.UserAuthorizeResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type UserAuthorize struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	Timeline primitive.DateTime `json:"create_timeline" bson:"create_timeline"`
	Comment  primitive.DateTime `json:"create_comment" bson:"create_comment"`
	Reply    primitive.DateTime `json:"create_reply" bson:"create_reply"`
	Message  primitive.DateTime `json:"create_message" bson:"create_message"`
	Live     primitive.DateTime `json:"create_live" bson:"create_live"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class UserAuthorizeEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var timeline: Long = 0
    var comment: Long = 0
    var reply: Long = 0
    var message: Long = 0
    var live: Long = 0
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun UserAuthorizeEntity.Companion.fromResponse(userAuthorizeResponse: UserAuthorizeResponse): UserAuthorizeEntity {
    val userAuthorizeEntity = UserAuthorizeEntity()
    userAuthorizeEntity.id = userAuthorizeResponse.id
    userAuthorizeEntity.timeline = userAuthorizeResponse.timeline
    userAuthorizeEntity.comment = userAuthorizeResponse.comment
    userAuthorizeEntity.reply = userAuthorizeResponse.reply
    userAuthorizeEntity.message = userAuthorizeResponse.message
    userAuthorizeEntity.live = userAuthorizeResponse.live
    userAuthorizeEntity.createdAt = userAuthorizeResponse.log.createdAt
    userAuthorizeEntity.updatedAt = userAuthorizeResponse.log.updatedAt
    return userAuthorizeEntity
}