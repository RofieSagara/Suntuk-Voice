package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
type Reaction struct {
	ID          primitive.ObjectID  `json:"id,omitempty" bson:"_id,omitempty"`
	AuthorID    primitive.ObjectID  `json:"author_id" bson:"author_id"`
	AuthorName  string              `json:"author_name" bson:"author_name"`
	Title       string              `json:"title" bson:"title"`
	Description string              `json:"description" bson:"description"`
	Key         string              `json:"key" bson:"key"`
	ThumbKey    string              `json:"thumb_key" bson:"thumb_key"`
	Price       float64             `json:"price" bson:"price"`
	Discount    float64             `json:"discount" bson:"discount"`
	CurrentType ReactionCurrentType `json:"current_type" bson:"current_type"`
	Available   bool                `json:"available" bson:"available"`
	Log         *DataLog            `json:"log" bson:"log"`
}
 */
data class ReactionResponse(
    val id: String,
    @SerializedName("author_id") val authorID: String,
    @SerializedName("author_name") val authorName: String,
    val title: String,
    val description: String,
    val key: String,
    @SerializedName("thumb_key") val thumbKey: String,
    val price: Double,
    val discount: Double,
    @SerializedName("current_type") val currentType: String,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type ReactionCount struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	BuyCount  int64              `json:"buy_count" bson:"buy_count"`
	UsedCount int64              `json:"used_count" bson:"used_count"`
	Log       *DataLog           `json:"log" bson:"log"`
}
 */
data class ReactionCountResponse(
    val id: String,
    @SerializedName("buy_count") val buyCount: Long,
    @SerializedName("used_count") val usedCount: Long,
    val log: DataLogResponse
)

/*
type ReactionAuthor struct {
	ID        primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UID       string             `json:"uid" bson:"uid"`
	Email     string             `json:"email" bson:"email"`
	Available bool               `json:"available" bson:"available"`
	Log       *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class ReactionAuthorResponse(
    val id: String,
    val uid: String,
    val email: String,
    val available: Boolean,
    val log: DataLogResponse
)