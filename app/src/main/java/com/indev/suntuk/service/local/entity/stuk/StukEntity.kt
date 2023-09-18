package com.indev.suntuk.service.local.entity.stuk

import com.indev.suntuk.service.api.response.StukResponse
import com.indev.suntuk.service.local.entity.Content
import com.indev.suntuk.service.local.entity.StukSettings
import com.indev.suntuk.service.local.entity.fromResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type Stuk struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Parent      primitive.ObjectID `json:"parent" bson:"parent"`
	ThreadID    string             `json:"thread_id" bson:"thread_id"`
	Nickname    string             `json:"nickname" bson:"nickname"`
	Profile     string             `json:"profile" bson:"profile"`
	IsAnonymous bool               `json:"is_anonymous" bson:"is_anonymous"`
	Content     *Content           `json:"content" bson:"content"`
	Likes       int64              `json:"likes" bson:"likes"`
	Comments    int64              `json:"comments" bson:"comments"`
	Replies     int64              `json:"replies" bson:"replies"`
	Reactions   int64              `json:"reactions" bson:"reactions"`
	Settings    *StukSettings      `json:"settings" bson:"settings"`
	Available   bool               `json:"available" bson:"available"`
	Log         *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class StukEntity: RealmObject {
    @PrimaryKey
    var id: String = ""
    var userId: String = ""
    var parentId: String? = null
    var threadId: String = ""
    var nickname: String = ""
    var profile: String = ""
    var isAnonymous: Boolean = false
    var content: Content? = null
    var likes: Int = 0
    var comments: Int = 0
    var replies: Int = 0
    var reactions: Int = 0
    var settings: StukSettings? = null
    var available: Boolean = true
    var isTimeline: Boolean = false
    var isFavorite: Boolean = false
    var isOwner: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun StukEntity.Companion.fromResponse(response: StukResponse): StukEntity {
    val entity = StukEntity()
    entity.id = response.id
    entity.userId = response.userID
    entity.parentId = response.parent
    entity.threadId = response.threadID
    entity.nickname = response.nickname
    entity.profile = response.profile
    entity.isAnonymous = response.isAnonymous
    entity.content = Content.fromResponse(response.content)
    entity.likes = response.likes.toInt()
    entity.comments = response.comments.toInt()
    entity.replies = response.replies.toInt()
    entity.reactions = response.reactions.toInt()
    entity.settings = StukSettings.fromResponse(response.settings)
    entity.available = response.available
    entity.createdAt = response.log.createdAt
    entity.updatedAt = response.log.updatedAt
    return entity
}