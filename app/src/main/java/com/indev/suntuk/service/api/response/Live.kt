package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
type Live struct {
	ID              primitive.ObjectID `json:"id" bson:"_id,omitempty"`
	Token           string             `json:"token" bson:"token"`
	HLS             string             `json:"hls" bson:"hls"`
	Title           string             `json:"title" bson:"title"`
	Background      string             `json:"background" bson:"background"`
	BackgroundThumb string             `json:"background_thumb" bson:"background_thumb"`
	Profile         string             `json:"profile" bson:"profile"`
	Nickname        string             `json:"nickname" bson:"nickname"`
	Category        string             `json:"category" bson:"category"`
	IsLive          bool               `json:"is_live" bson:"is_live"`
	Available       bool               `json:"available" bson:"available"`
	Log             *DataLog           `json:"log" bson:"log"`
}
 */
data class LiveResponse(
    val id: String,
    val token: String,
    val hls: String,
    val title: String,
    val background: String,
    @SerializedName("background_thumb") val backgroundThumb: String,
    val profile: String,
    val nickname: String,
    val category: String,
    @SerializedName("is_live") val isLive: Boolean,
    val available: Boolean,
    val log: DataLogResponse
)

/*
type LiveCounter struct {
	ID      primitive.ObjectID `json:"id" bson:"_id,omitempty"`
	Likes   int64              `json:"likes" bson:"likes"`
	Comment int64              `json:"comment" bson:"comment"`
	Share   int64              `json:"share" bson:"share"`
	Log     *DataLog           `json:"log" bson:"log"`
}
 */
data class LiveCounterResponse(
    val id: String,
    val likes: Long,
    val comment: Long,
    val share: Long,
    val log: DataLogResponse
)

/*
type LiveMessage struct {
	ID      primitive.ObjectID `json:"id" bson:"_id,omitempty"`
	LiveID  primitive.ObjectID `json:"live_id" bson:"live_id"`
	UserID  primitive.ObjectID `json:"user_id" bson:"user_id"`
	ChatID  string             `json:"chat_id" bson:"chat_id"`
	Message *Message           `json:"message" bson:"message"`
	Log     *DataLog           `json:"log" bson:"log"`
}
 */
data class LiveMessageResponse(
    val id: String,
    @SerializedName("live_id") val liveID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("chat_id") val chatID: String,
    val message: MessageResponse,
    val log: DataLogResponse
)