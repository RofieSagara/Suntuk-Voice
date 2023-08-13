package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

data class DataLogResponse(
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("updated_at") val updatedAt: Long,
)

/*
type ContentImage struct {
	Key         string `json:"key" bson:"key"`
	URL         string `json:"url" bson:"url"`
	Width       int64  `json:"width" bson:"width"`
	Height      int64  `json:"height" bson:"height"`
	Size        int64  `json:"size" bson:"size"`
	ThumbKey    string `json:"thumb_key" bson:"thumb_key"`
	ThumbURL    string `json:"thumb_url" bson:"thumb_url"`
	ThumbWidth  int64  `json:"thumb_width" bson:"thumb_width"`
	ThumbHeight int64  `json:"thumb_height" bson:"thumb_height"`
	ThumbSize   int64  `json:"thumb_size" bson:"thumb_size"`
}
 */
data class ContentImageResponse(
    val key: String,
    val url: String,
    val width: Long,
    val height: Long,
    val size: Long,
    @SerializedName("thumb_key") val thumbKey: String,
    @SerializedName("thumb_url") val thumbURL: String,
    @SerializedName("thumb_width") val thumbWidth: Long,
    @SerializedName("thumb_height") val thumbHeight: Long,
    @SerializedName("thumb_size") val thumbSize: Long
)

/*
type ContentVoice struct {
	Key      string `json:"key" bson:"key"`
	URL      string `json:"url" bson:"url"`
	Duration int64  `json:"duration" bson:"duration"`
	Size     int64  `json:"size" bson:"size"`
}
 */
data class ContentVoiceResponse(
    val key: String,
    val url: String,
    val duration: Long,
    val size: Long
)

/*
type ContentSticker struct {
	StickerID primitive.ObjectID `json:"sticker_id" bson:"sticker_id"`
	Key       string             `json:"key" bson:"key"`
	URL       string             `json:"url" bson:"url"`
}
 */
data class ContentStickerResponse(
    @SerializedName("sticker_id") val stickerID: String,
    val key: String,
    val url: String
)

/*
type Content struct {
	Text    string          `json:"text" bson:"text"`
	Images  []*ContentImage `json:"images" bson:"images"`
	Voice   *ContentVoice   `json:"voice" bson:"voice"`
	Sticker *ContentSticker `json:"sticker" bson:"sticker"`
}
 */
data class ContentResponse(
    val text: String,
    val images: List<ContentImageResponse>,
    val voice: ContentVoiceResponse,
    val sticker: ContentStickerResponse
)

/*
type StukSettings struct {
	Comment bool `json:"comment" bson:"comment"`
	Reply   bool `json:"reply" bson:"reply"`
	Chat    bool `json:"chat" bson:"chat"`
}
 */
data class StukSettingsResponse(
    val comment: Boolean,
    val reply: Boolean,
    val chat: Boolean
)

/*
type Message struct {
	Text    string          `json:"text" bson:"text"`
	Image   *ContentImage   `json:"image" bson:"image"`
	Voice   *ContentVoice   `json:"voice" bson:"voice"`
	Sticker *ContentSticker `json:"sticker" bson:"sticker"`
}
 */
data class MessageResponse(
    val text: String,
    val image: ContentImageResponse,
    val voice: ContentVoiceResponse,
    val sticker: ContentStickerResponse
)

/*
type ParentMessage struct {
	ID       primitive.ObjectID `json:"id" bson:"_id"`
	UserID   primitive.ObjectID `json:"user_id" bson:"user_id"`
	Profile  string             `json:"profile" bson:"profile"`
	Nickname string             `json:"nickname" bson:"nickname"`
	Text     string             `json:"text" bson:"text"`
	Status   ParentMessageType  `json:"status" bson:"status"`
}
 */
data class ParentMessageResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    val profile: String,
    val nickname: String,
    val text: String,
    val status: String
)