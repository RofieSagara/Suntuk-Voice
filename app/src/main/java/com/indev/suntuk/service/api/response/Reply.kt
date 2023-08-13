package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

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
data class ReplyResponse(
    val id: String,
    @SerializedName("stuk_id") val stukID: String,
    @SerializedName("comment_id") val commentID: String,
    @SerializedName("user_id") val userID: String,
    val nickname: String,
    val profile: String,
    val content: ContentResponse,
    @SerializedName("is_anonymous") val isAnonymous: Boolean,
    val likes: Long,
    val reactions: Long,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type ReplyDeleted struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	DeleteBy primitive.ObjectID `json:"delete_by" bson:"delete_by"`
	Reason   string             `json:"reason" bson:"reason"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class ReplyDeletedResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("delete_by") val deleteBy: String,
    val reason: String,
    val log: DataLogResponse
)

/*
type ReplyReaction struct {
	ID         primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	ReplyID    primitive.ObjectID `json:"reply_id" bson:"reply_id"`
	UserID     primitive.ObjectID `json:"user_id" bson:"user_id"`
	ReactionID primitive.ObjectID `json:"reaction_id" bson:"reaction_id"`
	Key        string             `json:"key" bson:"key"`
	ThumbKey   string             `json:"thumb_key" bson:"thumb_key"`
	Log        *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class ReplyReactionResponse(
    val id: String,
    @SerializedName("reply_id") val replyID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("reaction_id") val reactionID: String,
    val key: String,
    @SerializedName("thumb_key") val thumbKey: String,
    val log: DataLogResponse
)

/*
type ReplyLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	ReplyID   primitive.ObjectID `json:"reply_id" bson:"reply_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class ReplyLikeResponse(
    val id: String,
    @SerializedName("reply_id") val replyID: String,
    @SerializedName("user_id") val userID: String,
    val available: Boolean,
    val log: DataLogResponse
)