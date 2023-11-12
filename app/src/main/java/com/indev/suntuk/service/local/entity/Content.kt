package com.indev.suntuk.service.local.entity

import com.google.gson.annotations.SerializedName
import com.indev.suntuk.service.api.response.ContentImageResponse
import com.indev.suntuk.service.api.response.ContentResponse
import com.indev.suntuk.service.api.response.ContentStickerResponse
import com.indev.suntuk.service.api.response.ContentVoiceResponse
import com.indev.suntuk.service.api.response.StukSettingsResponse
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class Content: RealmObject {
    var text: String = ""
    var images: RealmList<ContentImage> = realmListOf()
    var voice: ContentVoice? = null
    var sticker: ContentSticker? = null

    companion object {}
}
fun Content.Companion.fromResponse(contentResponse: ContentResponse): Content {
    val content = Content()
    content.text = contentResponse.text
    content.images = contentResponse.images.map { ContentImage.fromResponse(it) }.toRealmList()
    content.voice = contentResponse.voice.let { ContentVoice.fromResponse(it) }
    content.sticker = contentResponse.sticker.let { ContentSticker.fromResponse(it) }
    return content
}

class ContentImage: RealmObject {
    var key: String = ""
    var url: String = ""
    var width: Long = 0
    var height: Long = 0
    var size: Long = 0
    var thumbKey: String = ""
    var thumbURL: String = ""
    var thumbWidth: Long = 0
    var thumbHeight: Long = 0
    var thumbSize: Long = 0

    companion object {}
}

fun ContentImage.Companion.fromResponse(contentImageResponse: ContentImageResponse): ContentImage {
    return ContentImage().apply {
        key = contentImageResponse.key
        url = contentImageResponse.url
        width = contentImageResponse.width
        height = contentImageResponse.height
        size = contentImageResponse.size
        thumbKey = contentImageResponse.thumbKey
        thumbURL = contentImageResponse.thumbURL
        thumbWidth = contentImageResponse.thumbWidth
        thumbHeight = contentImageResponse.thumbHeight
        thumbSize = contentImageResponse.thumbSize
    }
}

class ContentVoice: RealmObject {
    var key: String = ""
    var url: String = ""
    var duration: Long = 0
    var size: Long = 0
    companion object
}

fun ContentVoice.Companion.fromResponse(contentVoiceResponse: ContentVoiceResponse): ContentVoice {
    return ContentVoice().apply {
        key = contentVoiceResponse.key
        url = contentVoiceResponse.url
        duration = contentVoiceResponse.duration
        size = contentVoiceResponse.size
    }
}

class ContentSticker: RealmObject {
    var stickerID: String = ""
    var key: String = ""
    var url: String = ""
    companion object {}
}

fun ContentSticker.Companion.fromResponse(contentStickerResponse: ContentStickerResponse): ContentSticker {
    return ContentSticker().apply {
        stickerID = contentStickerResponse.stickerID
        key = contentStickerResponse.key
        url = contentStickerResponse.url
    }
}

class StukSettings: RealmObject {
    var comment: Boolean = true
    var reply: Boolean = true
    var chat: Boolean = true

    companion object
}

fun StukSettings.Companion.fromResponse(stukSettingsResponse: StukSettingsResponse): StukSettings {
    return StukSettings().apply {
        comment = stukSettingsResponse.comment
        reply = stukSettingsResponse.reply
        chat = stukSettingsResponse.chat
    }
}