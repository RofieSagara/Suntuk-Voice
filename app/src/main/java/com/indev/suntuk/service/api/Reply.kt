package com.indev.suntuk.service.api

import com.google.gson.JsonObject
import com.indev.suntuk.service.api.dto.ReplyServiceCreateTextResponse
import com.indev.suntuk.service.api.dto.ReplyServiceFetchResponse
import com.indev.suntuk.service.api.dto.ReplyServiceGetResponse
import com.indev.suntuk.service.api.dto.ReplyServiceSearchResponse
import com.indev.suntuk.service.api.response.Res
import com.indev.suntuk.service.api.response.ResOK
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ReplyAPI {
    suspend fun createText(stukID: String, commentID: String, isAnonymous: Boolean, text: String): Res<ReplyServiceCreateTextResponse>
    suspend fun like(replyID: String): Res<String>
    suspend fun delete(replyID: String): Res<String>
    suspend fun get(replyID: String): Res<ReplyServiceGetResponse>
    suspend fun fetch(replyID: String, lastID: String): Res<ReplyServiceFetchResponse>
    suspend fun search(lastID: String, keyword: String): Res<ReplyServiceSearchResponse>
}

data class IReplyAPI(private val client: HttpClient): ReplyAPI {
    override suspend fun createText(
        stukID: String,
        commentID: String,
        isAnonymous: Boolean,
        text: String
    ): Res<ReplyServiceCreateTextResponse> {
       return client.post("/reply/text") {
           contentType(ContentType.Application.Json)
              setBody(JsonObject().apply {
                addProperty("stuk_id", stukID)
                addProperty("comment_id", commentID)
                addProperty("is_anonymous", isAnonymous)
                addProperty("content_text", text)
              })
       }.body()
    }

    override suspend fun like(replyID: String): Res<String> {
        return client.post("/reply/like") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("reply_id", replyID)
            })
        }.body()
    }

    override suspend fun delete(replyID: String): Res<String> {
        return client.delete("/reply") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("reply_id", replyID)
            })
        }.body()
    }

    override suspend fun get(replyID: String): Res<ReplyServiceGetResponse> {
        return client.get("/reply") {
            parameter("reply_id", replyID)
        }.body()
    }

    override suspend fun fetch(replyID: String, lastID: String): Res<ReplyServiceFetchResponse> {
        return client.get("/reply/fetch") {
            parameter("reply_id", replyID)
            parameter("last_id", lastID)
        }.body()
    }

    override suspend fun search(lastID: String, keyword: String): Res<ReplyServiceSearchResponse> {
        return client.get("/reply/search") {
            parameter("last_id", lastID)
            parameter("keyword", keyword)
        }.body()
    }
}