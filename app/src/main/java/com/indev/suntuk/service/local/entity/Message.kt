package com.indev.suntuk.service.local.entity

import com.indev.suntuk.service.api.response.MessageResponse
import com.indev.suntuk.service.api.response.ParentMessageResponse

/*
type Message struct {
	Text    string          `json:"text" bson:"text"`
	Image   *ContentImage   `json:"image" bson:"image"`
	Voice   *ContentVoice   `json:"voice" bson:"voice"`
	Sticker *ContentSticker `json:"sticker" bson:"sticker"`
}
 */
class Message(
    val text: String,
    val image: ContentImage,
    val voice: ContentVoice,
    val sticker: ContentSticker
) {
    companion object {}
}

fun Message.Companion.fromResponse(messageResponse: MessageResponse): Message {
    return Message(
        messageResponse.text,
        messageResponse.image.let { ContentImage.fromResponse(it) },
        messageResponse.voice.let { ContentVoice.fromResponse(it) },
        messageResponse.sticker.let { ContentSticker.fromResponse(it) }
    )
}

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
class ParentMessage(
    val id: String,
    val userId: String,
    val profile: String,
    val nickname: String,
    val text: String,
    val status: String
) {
    companion object {}
}
fun ParentMessage.Companion.fromResponse(parentMessageResponse: ParentMessageResponse): ParentMessage {
    return ParentMessage(
        parentMessageResponse.id,
        parentMessageResponse.userID,
        parentMessageResponse.profile,
        parentMessageResponse.nickname,
        parentMessageResponse.text,
        parentMessageResponse.status
    )
}