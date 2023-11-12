package com.indev.suntuk.service.local.entity.reply

import com.indev.suntuk.service.api.response.ReplyResponse
import com.indev.suntuk.service.local.entity.Content
import com.indev.suntuk.service.local.entity.fromResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type Reply struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID      primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	CommentID   primitive.ObjectID `json:"comment_id" bson:"comment_id"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Nickname    string             `json:"nickname" bson:"nickname"`
	Profile     string             `json:"profile" bson:"profile"`
	Content     *Content           `json:"content" bson:"content"`
	IsAnonymous bool               `json:"is_anonymous" bson:"is_anonymous"`
	Likes       int64              `json:"likes" bson:"likes"`
	Reactions   int64              `json:"reactions" bson:"reactions"`
	Available   bool               `json:"available" bson:"available"`
	Log         *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class ReplyEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var stukId: String = ""
    var commentId: String = ""
    var userId: String = ""
    var nickname: String = ""
    var profile: String = ""
    var content: Content? = null
    var isAnonymous: Boolean = false
    var likes: Long = 0
    var reactions: Long = 0
    var available: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun ReplyEntity.Companion.fromResponse(response: ReplyResponse): ReplyEntity {
    val entity = ReplyEntity()
    entity.id = response.id
    entity.stukId = response.stukID
    entity.commentId = response.commentID
    entity.userId = response.userID
    entity.nickname = response.nickname
    entity.profile = response.profile
    entity.content = Content.fromResponse(response.content)
    entity.isAnonymous = response.isAnonymous
    entity.likes = response.likes
    entity.reactions = response.reactions
    entity.available = response.available
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}