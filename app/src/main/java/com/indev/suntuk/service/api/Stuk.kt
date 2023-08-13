package com.indev.suntuk.service.api

import com.google.gson.JsonObject
import com.indev.suntuk.service.api.dto.StukServiceCreateImageResponse
import com.indev.suntuk.service.api.dto.StukServiceCreateTextResponse
import com.indev.suntuk.service.api.dto.StukServiceCreateThreadImageResponse
import com.indev.suntuk.service.api.dto.StukServiceCreateThreadTextResponse
import com.indev.suntuk.service.api.dto.StukServiceCreateThreadVoiceResponse
import com.indev.suntuk.service.api.dto.StukServiceCreateVoiceResponse
import com.indev.suntuk.service.api.dto.StukServiceFetchOwnerResponse
import com.indev.suntuk.service.api.dto.StukServiceFetchTimelineResponse
import com.indev.suntuk.service.api.dto.StukServiceGetResponse
import com.indev.suntuk.service.api.dto.StukServiceQuoteResponse
import com.indev.suntuk.service.api.dto.StukServiceSearchResponse
import com.indev.suntuk.service.api.response.Res
import com.indev.suntuk.service.api.response.ResOK
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File

/*
type StukService interface {
	CreateText(ctx context.Context, params *StukServiceCreateTextDTO) (*StukServiceCreateTextResponse, error)
	CreateImage(ctx context.Context, params *StukServiceCreateImageDTO) (*StukServiceCreateImageResponse, error)
	CreateVoice(ctx context.Context, params *StukServiceCreateVoiceDTO) (*StukServiceCreateVoiceResponse, error)
	Quote(ctx context.Context, params *StukServiceQuoteDTO) (*StukServiceQuoteResponse, error)
	CreateThreadText(ctx context.Context, params *StukServiceCreateThreadTextDTO) (*StukServiceCreateThreadTextResponse, error)
	CreateThreadImage(ctx context.Context, params *StukServiceCreateThreadImageDTO) (*StukServiceCreateThreadImageResponse, error)
	CreateThreadVoice(ctx context.Context, params *StukServiceCreateThreadVoiceDTO) (*StukServiceCreateThreadVoiceResponse, error)
	Like(ctx context.Context, params *StukServiceLikeDTO) error
	Remove(ctx context.Context, params *StukServiceRemoveDTO) error
	Get(ctx context.Context, params *StukServiceGetDTO) (*StukServiceGetResponse, error)
	FetchOwner(ctx context.Context, params *StukServiceFetchOwnerDTO) (*StukServiceFetchOwnerResponse, error)
	FetchTimeline(ctx context.Context, params *StukServiceFetchTimelineDTO) (*StukServiceFetchTimelineResponse, error)
	Search(ctx context.Context, params *StukServiceSearchDTO) (*StukServiceSearchResponse, error)
}
 */
interface StukAPI {
    suspend fun createText(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String): Res<StukServiceCreateTextResponse>
    suspend fun createImage(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, image: List<File>): Res<StukServiceCreateImageResponse>
    suspend fun createVoice(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, voice: File): Res<StukServiceCreateVoiceResponse>
    suspend fun quote(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, parentID: String): Res<StukServiceQuoteResponse>
    suspend fun createThreadText(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, parentID: String): Res<StukServiceCreateThreadTextResponse>
    suspend fun createThreadImage(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, image: List<File>, parentID: String): Res<StukServiceCreateThreadImageResponse>
    suspend fun createThreadVoice(isAnonymous: Boolean, commentActive: Boolean, replyActive: Boolean, chatActive: Boolean, text: String, voice: File, parentID: String): Res<StukServiceCreateThreadVoiceResponse>
    suspend fun like(stukID: String)
    suspend fun remove(stukID: String)
    suspend fun get(id: String): Res<StukServiceGetResponse>
    suspend fun fetchOwner(lastID: String): Res<StukServiceFetchOwnerResponse>
    suspend fun fetchTimeline(lastID: String): Res<StukServiceFetchTimelineResponse>
    suspend fun search(keyword: String, lastID: String): Res<StukServiceSearchResponse>
}

class IStukAPI(private val client: HttpClient): StukAPI {
    override suspend fun createText(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String
    ): Res<StukServiceCreateTextResponse> {
        val jsonSettings = JsonObject()
        jsonSettings.addProperty("comment", commentActive)
        jsonSettings.addProperty("reply", replyActive)
        jsonSettings.addProperty("chat", chatActive)

        val jsonBody = JsonObject()
        jsonBody.addProperty("is_anonymous", isAnonymous)
        jsonBody.add("settings", jsonSettings)
        jsonBody.addProperty("content_text", text)

        return client.post("/stuk/text") {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }.body()
    }

    override suspend fun createImage(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        image: List<File>
    ): Res<StukServiceCreateImageResponse> {
        return client.post("/stuk/image") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("is_anonymous", isAnonymous.toString())
                    append("is_comment", commentActive.toString())
                    append("is_reply", replyActive.toString())
                    append("is_chat", chatActive.toString())
                    append("text", text)
                    append("files", image.joinToString(","))
                    image.forEachIndexed { index, file ->
                        append("files", file.readBytes(), Headers.build {
                            this.append(HttpHeaders.ContentType, "image/*")
                            this.append(HttpHeaders.ContentDisposition, "filename=$index.${file.extension}")
                        })
                    }
                }
            ))
        }.body()
    }

    override suspend fun createVoice(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        voice: File
    ): Res<StukServiceCreateVoiceResponse> {
        return client.post("/stuk/voice") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("is_anonymous", isAnonymous.toString())
                    append("is_comment", commentActive.toString())
                    append("is_reply", replyActive.toString())
                    append("is_chat", chatActive.toString())
                    append("text", text)
                    append("file", voice.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "audio/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${voice.name}")
                    })
                }
            ))
        }.body()
    }

    override suspend fun quote(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        parentID: String
    ): Res<StukServiceQuoteResponse> {
        val jsonSettings = JsonObject()
        jsonSettings.addProperty("comment", commentActive)
        jsonSettings.addProperty("reply", replyActive)
        jsonSettings.addProperty("chat", chatActive)

        val jsonBody = JsonObject()
        jsonBody.addProperty("is_anonymous", isAnonymous)
        jsonBody.add("settings", jsonSettings)
        jsonBody.addProperty("content_text", text)
        jsonBody.addProperty("parent_id", parentID)

        return client.post("/stuk/quote") {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }.body()
    }

    override suspend fun createThreadText(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        parentID: String
    ): Res<StukServiceCreateThreadTextResponse> {
        val jsonSettings = JsonObject()
        jsonSettings.addProperty("comment", commentActive)
        jsonSettings.addProperty("reply", replyActive)
        jsonSettings.addProperty("chat", chatActive)

        val jsonBody = JsonObject()
        jsonBody.addProperty("is_anonymous", isAnonymous)
        jsonBody.add("settings", jsonSettings)
        jsonBody.addProperty("content_text", text)
        jsonBody.addProperty("parent", parentID)

        return client.post("/stuk/thread-text") {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }.body()
    }

    override suspend fun createThreadImage(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        image: List<File>,
        parentID: String
    ): Res<StukServiceCreateThreadImageResponse> {
        return client.post("/stuk/thread-image") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("is_anonymous", isAnonymous.toString())
                    append("is_comment", commentActive.toString())
                    append("is_reply", replyActive.toString())
                    append("is_chat", chatActive.toString())
                    append("text", text)
                    append("parent", parentID)
                    append("files", image.joinToString(","))
                    image.forEachIndexed { index, file ->
                        append("files", file.readBytes(), Headers.build {
                            this.append(HttpHeaders.ContentType, "image/*")
                            this.append(HttpHeaders.ContentDisposition, "filename=$index.${file.extension}")
                        })
                    }
                }
            ))
        }.body()
    }

    override suspend fun createThreadVoice(
        isAnonymous: Boolean,
        commentActive: Boolean,
        replyActive: Boolean,
        chatActive: Boolean,
        text: String,
        voice: File,
        parentID: String
    ): Res<StukServiceCreateThreadVoiceResponse> {
        return client.post("/stuk/thread-voice") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("is_anonymous", isAnonymous.toString())
                    append("is_comment", commentActive.toString())
                    append("is_reply", replyActive.toString())
                    append("is_chat", chatActive.toString())
                    append("text", text)
                    append("parent", parentID)
                    append("file", voice.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "audio/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${voice.name}")
                    })
                }
            ))
        }.body()
    }

    override suspend fun like(stukID: String) {
        client.put("/stuk/like") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("stuk_id", stukID)
            })
        }.body<ResOK>()
    }

    override suspend fun remove(stukID: String) {
        client.delete("/stuk") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("stuk_id", stukID)
            })
        }.body<ResOK>()
    }

    override suspend fun get(id: String): Res<StukServiceGetResponse> {
        return client.get("/stuk") {
            parameter("stuk_id", id)
        }.body()
    }

    override suspend fun fetchOwner(lastID: String): Res<StukServiceFetchOwnerResponse> {
        return client.get("/stuk/owner") {
            parameter("last_id", lastID)
        }.body()
    }

    override suspend fun fetchTimeline(lastID: String): Res<StukServiceFetchTimelineResponse> {
        return client.get("/stuk/timeline") {
            parameter("last_id", lastID)
        }.body()
    }

    override suspend fun search(keyword: String, lastID: String): Res<StukServiceSearchResponse> {
        return client.get("/stuk/search") {
            parameter("keyword", keyword)
            parameter("last_id", lastID)
        }.body()
    }

}