package com.indev.suntuk.service.local.entity

import com.indev.suntuk.service.api.response.MessageResponse
import com.indev.suntuk.service.api.response.ParentMessageResponse
import io.realm.kotlin.types.RealmObject

/*
type Message struct {
	Text    string          `json:"text" bson:"text"`
	Image   *ContentImage   `json:"image" bson:"image"`
	Voice   *ContentVoice   `json:"voice" bson:"voice"`
	Sticker *ContentSticker `json:"sticker" bson:"sticker"`
}
 */
class Message: RealmObject {
    var text: String = ""
    var image: ContentImage? = null
    var voice: ContentVoice? = null
    var sticker: ContentSticker? = null
    companion object
}

fun Message.Companion.fromResponse(messageResponse: MessageResponse): Message {
    return Message().apply {
        text = messageResponse.text
        image = messageResponse.image.let { ContentImage.fromResponse(it) }
        voice = messageResponse.voice.let { ContentVoice.fromResponse(it) }
        sticker = messageResponse.sticker.let { ContentSticker.fromResponse(it) }
    }
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
class ParentMessage: RealmObject {
    var id: String = ""
    var userId: String = ""
    var profile: String = ""
    var nickname: String = ""
    var text: String = ""
    var status: String = ""
    companion object
}
fun ParentMessage.Companion.fromResponse(parentMessageResponse: ParentMessageResponse): ParentMessage {
    return ParentMessage().apply {
        id = parentMessageResponse.id
        userId = parentMessageResponse.userID
        profile = parentMessageResponse.profile
        nickname = parentMessageResponse.nickname
        text = parentMessageResponse.text
        status = parentMessageResponse.status
    }
}