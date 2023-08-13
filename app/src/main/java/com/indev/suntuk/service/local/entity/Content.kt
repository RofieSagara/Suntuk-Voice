package com.indev.suntuk.service.local.entity

import com.google.gson.annotations.SerializedName
import com.indev.suntuk.service.api.response.ContentImageResponse
import com.indev.suntuk.service.api.response.ContentResponse
import com.indev.suntuk.service.api.response.ContentStickerResponse
import com.indev.suntuk.service.api.response.ContentVoiceResponse
import com.indev.suntuk.service.api.response.StukSettingsResponse

class Content {
    var text: String = ""
    var images: List<ContentImage> = listOf()
    var voice: ContentVoice? = null
    var sticker: ContentSticker? = null

    companion object {}
}
fun Content.Companion.fromResponse(contentResponse: ContentResponse): Content {
    val content = Content()
    content.text = contentResponse.text
    content.images = contentResponse.images.map { ContentImage.fromResponse(it) }
    content.voice = contentResponse.voice.let { ContentVoice.fromResponse(it) }
    content.sticker = contentResponse.sticker.let { ContentSticker.fromResponse(it) }
    return content
}

class ContentImage(
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
) {
    companion object {}
}

fun ContentImage.Companion.fromResponse(contentImageResponse: ContentImageResponse): ContentImage {
    return ContentImage(
        contentImageResponse.key,
        contentImageResponse.url,
        contentImageResponse.width,
        contentImageResponse.height,
        contentImageResponse.size,
        contentImageResponse.thumbKey,
        contentImageResponse.thumbURL,
        contentImageResponse.thumbWidth,
        contentImageResponse.thumbHeight,
        contentImageResponse.thumbSize
    )
}

class ContentVoice(
    val key: String,
    val url: String,
    val duration: Long,
    val size: Long
) {
    companion object {}
}

fun ContentVoice.Companion.fromResponse(contentVoiceResponse: ContentVoiceResponse): ContentVoice {
    return ContentVoice(
        contentVoiceResponse.key,
        contentVoiceResponse.url,
        contentVoiceResponse.duration,
        contentVoiceResponse.size
    )
}

class ContentSticker(
    @SerializedName("sticker_id") val stickerID: String,
    val key: String,
    val url: String
) {
    companion object {}
}

fun ContentSticker.Companion.fromResponse(contentStickerResponse: ContentStickerResponse): ContentSticker {
    return ContentSticker(
        contentStickerResponse.stickerID,
        contentStickerResponse.key,
        contentStickerResponse.url
    )
}

class StukSettings(
    val comment: Boolean,
    val reply: Boolean,
    val chat: Boolean
) {
    companion object {}
}

fun StukSettings.Companion.fromResponse(stukSettingsResponse: StukSettingsResponse): StukSettings {
    return StukSettings(
        stukSettingsResponse.comment,
        stukSettingsResponse.reply,
        stukSettingsResponse.chat
    )
}