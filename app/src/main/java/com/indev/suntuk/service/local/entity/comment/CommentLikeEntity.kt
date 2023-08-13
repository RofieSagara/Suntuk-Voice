package com.indev.suntuk.service.local.entity.comment

import com.indev.suntuk.service.api.response.CommentLikeResponse
import io.realm.kotlin.types.RealmObject

/*
type CommentLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	CommentID primitive.ObjectID `json:"comment_id" bson:"comment_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class CommentLikeEntity : RealmObject {
    var id: String = ""
    var commentId: String = ""
    var userId: String = ""
    var available: Boolean = false
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}
fun CommentLikeEntity.Companion.fromResponse(commentLikeResponse: CommentLikeResponse): CommentLikeEntity {
    val commentLikeEntity = CommentLikeEntity()
    commentLikeEntity.id = commentLikeResponse.id
    commentLikeEntity.commentId = commentLikeResponse.commentID
    commentLikeEntity.userId = commentLikeResponse.userID
    commentLikeEntity.available = commentLikeResponse.available
    commentLikeEntity.createdAt = commentLikeResponse.log.createdAt
    commentLikeEntity.updatedAt = commentLikeResponse.log.updatedAt
    return commentLikeEntity
}