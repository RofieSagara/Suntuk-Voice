package com.indev.suntuk.service.api

import com.google.gson.JsonObject
import com.indev.suntuk.service.api.dto.CommentServiceCreateTextResponse
import com.indev.suntuk.service.api.dto.CommentServiceFetchResponse
import com.indev.suntuk.service.api.dto.CommentServiceGetResponse
import com.indev.suntuk.service.api.dto.CommentServiceSearchResponse
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

interface CommentAPI {
    suspend fun createText(stukID: String, isAnonymous: Boolean, text: String): Res<CommentServiceCreateTextResponse>
    suspend fun like(stukID: String, commentID: String)
    suspend fun delete(commentID: String)
    suspend fun get(commentID: String): Res<CommentServiceGetResponse>
    suspend fun fetch(stukID: String, lastID: String): Res<List<CommentServiceFetchResponse>>
    suspend fun search(lastID: String, keyword: String): Res<List<CommentServiceSearchResponse>>
}

class IComment(private val client: HttpClient): CommentAPI {
    override suspend fun createText(
        stukID: String,
        isAnonymous: Boolean,
        text: String
    ): Res<CommentServiceCreateTextResponse> {
        return client.post("/comment/text") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("stuk_id", stukID)
                addProperty("is_anonymous", isAnonymous)
                addProperty("content_text", text)
            })
        }.body()
    }

    override suspend fun like(stukID: String, commentID: String) {
        client.post("/comment/like") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("stuk_id", stukID)
                addProperty("comment_id", commentID)
            })
        }.body<ResOK>()
    }

    override suspend fun delete(commentID: String) {
        client.delete("/comment/delete") {
            contentType(ContentType.Application.Json)
            setBody(JsonObject().apply {
                addProperty("comment_id", commentID)
            })
        }.body<ResOK>()
    }

    override suspend fun get(commentID: String): Res<CommentServiceGetResponse> {
        return client.get("/comment/get") {
            parameter("comment_id", commentID)
        }.body()
    }

    override suspend fun fetch(
        stukID: String,
        lastID: String
    ): Res<List<CommentServiceFetchResponse>> {
        return client.get("/comment/fetch") {
            parameter("stuk_id", stukID)
            parameter("last_id", lastID)
        }.body()
    }

    override suspend fun search(
        lastID: String,
        keyword: String
    ): Res<List<CommentServiceSearchResponse>> {
        return client.get("/comment/search") {
            parameter("last_id", lastID)
            parameter("keyword", keyword)
        }.body()
    }

}