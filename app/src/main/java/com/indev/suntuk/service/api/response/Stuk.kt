package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
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
 */
data class StukResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    val parent: String,
    @SerializedName("thread_id") val threadID: String,
    val nickname: String,
    val profile: String,
    @SerializedName("is_anonymous") val isAnonymous: Boolean,
    val content: ContentResponse,
    val likes: Long,
    val comments: Long,
    val replies: Long,
    val reactions: Long,
    val settings: StukSettingsResponse,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type StukDeleted struct {
	ID       primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	DeleteBy primitive.ObjectID `json:"delete_by" bson:"delete_by"`
	Reason   string             `json:"reason" bson:"reason"`
	Log      *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class StukDeletedResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("delete_by") val deleteBy: String,
    val reason: String,
    val log: DataLogResponse
)

/*
type StukReaction struct {
	ID         primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID     primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	UserID     primitive.ObjectID `json:"user_id" bson:"user_id"`
	ReactionID primitive.ObjectID `json:"reaction_id" bson:"reaction_id"`
	Key        string             `json:"key" bson:"key"`
	ThumbKey   string             `json:"thumb_key" bson:"thumb_key"`
	Log        *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class StukReactionResponse(
    val id: String,
    @SerializedName("stuk_id") val stukID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("reaction_id") val reactionID: String,
    val key: String,
    @SerializedName("thumb_key") val thumbKey: String,
    val log: DataLogResponse
)

/*
type StukLike struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	StukID    primitive.ObjectID `json:"stuk_id" bson:"stuk_id"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class StukLikeResponse(
    val id: String,
    @SerializedName("stuk_id") val stukID: String,
    @SerializedName("user_id") val userID: String,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type StukCount struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID    primitive.ObjectID `json:"user_id" bson:"user_id"`
	Date      string             `json:"date" bson:"date"`
	Count     int64              `json:"count" bson:"count"`
	CreatedAt primitive.ObjectID `json:"created_at" bson:"created_at"`
}
 */
data class StukCountResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    val date: String,
    val count: Long,
    @SerializedName("created_at") val createdAt: String
)