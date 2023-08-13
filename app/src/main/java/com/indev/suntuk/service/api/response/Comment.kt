package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
type Comment struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID      primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Nickname    string             `json:"nickname" bson:"nickname"`
	Profile     string             `json:"profile" bson:"profile"`
	Content     *Content           `json:"content" bson:"content"`
	IsAnonymous bool               `json:"is_anonymous" bson:"is_anonymous"`
	Likes       int64              `json:"likes" bson:"likes"`
	Replies     int64              `json:"replies" bson:"replies"`
	Reactions   int64              `json:"reactions" bson:"reactions"`
	Available   bool               `json:"available" bson:"available"`
	Log         *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class CommentResponse(
    val id: String,
    @SerializedName("stuk_id") val stukID: String,
    @SerializedName("user_id") val userID: String,
    val nickname: String,
    val profile: String,
    val content: ContentResponse,
    @SerializedName("is_anonymous") val isAnonymous: Boolean,
    val likes: Long,
    val replies: Long,
    val reactions: Long,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type CommentDeleted struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	DeleteBy primitive.ObjectID `json:"delete_by" bson:"delete_by"`
	Reason   string             `json:"reason" bson:"reason"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class CommentDeletedResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("delete_by") val deleteBy: String,
    val reason: String,
    val log: DataLogResponse
)

/*
type CommentReaction struct {
	ID         primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	CommentID  primitive.ObjectID `json:"comment_id" bson:"comment_id"`
	UserID     primitive.ObjectID `json:"user_id" bson:"user_id"`
	ReactionID primitive.ObjectID `json:"reaction_id" bson:"reaction_id"`
	Key        string             `json:"key" bson:"key"`
	ThumbKey   string             `json:"thumb_key" bson:"thumb_key"`
	Log        *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class CommentReactionResponse(
    val id: String,
    @SerializedName("comment_id") val commentID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("reaction_id") val reactionID: String,
    val key: String,
    @SerializedName("thumb_key") val thumbKey: String,
    val log: DataLogResponse
)

/*
type CommentLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	CommentID primitive.ObjectID `json:"comment_id" bson:"comment_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class CommentLikeResponse(
    val id: String,
    @SerializedName("comment_id") val commentID: String,
    @SerializedName("user_id") val userID: String,
    val available: Boolean,
    val log: DataLogResponse
)