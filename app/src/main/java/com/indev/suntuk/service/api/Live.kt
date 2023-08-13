package com.indev.suntuk.service.api

import com.indev.suntuk.service.api.dto.LiveServiceCreateRoomResponse
import com.indev.suntuk.service.api.dto.LiveServiceFetchCategoryResponse
import com.indev.suntuk.service.api.dto.LiveServiceFetchLatestResponse
import com.indev.suntuk.service.api.dto.LiveServiceFetchPopularResponse
import com.indev.suntuk.service.api.dto.LiveServiceGetResponse
import com.indev.suntuk.service.api.dto.LiveServiceSearchResponse
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
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

interface LiveAPI {
    suspend fun createRoom(title: String, background: File, category: String, profile: String, nickname: String): Res<LiveServiceCreateRoomResponse>
    suspend fun deleteRoom()
    suspend fun getRoom(): Res<LiveServiceGetResponse>
    suspend fun fetchPopular(): Res<LiveServiceFetchPopularResponse>
    suspend fun fetchLatest(): Res<LiveServiceFetchLatestResponse>
    suspend fun fetchCategory(): Res<LiveServiceFetchCategoryResponse>
    suspend fun search(keyword: String): Res<List<LiveServiceSearchResponse>>
}

data class ILiveAPI(private val client: HttpClient): LiveAPI {
    override suspend fun createRoom(
        title: String,
        background: File,
        category: String,
        profile: String,
        nickname: String
    ): Res<LiveServiceCreateRoomResponse> {
        return client.post("/live") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("title", title)
                    append("background", background.readBytes(), Headers.build {
                        this.append(HttpHeaders.ContentType, "image/*")
                        this.append(HttpHeaders.ContentDisposition, "filename=${background.extension}")
                    })
                    append("category", category)
                    append("profile", profile)
                    append("nickname", nickname)
                }
            ))
        }.body()
    }

    override suspend fun deleteRoom() {
        client.delete("/live").body<ResOK>()
    }

    override suspend fun getRoom(): Res<LiveServiceGetResponse> {
        return client.get("/live").body()
    }

    override suspend fun fetchPopular(): Res<LiveServiceFetchPopularResponse> {
        return client.get("/live/popular").body()
    }

    override suspend fun fetchLatest(): Res<LiveServiceFetchLatestResponse> {
        return client.get("/live/latest").body()
    }

    override suspend fun fetchCategory(): Res<LiveServiceFetchCategoryResponse> {
        return client.get("/live/category").body()
    }

    override suspend fun search(keyword: String): Res<List<LiveServiceSearchResponse>> {
        return client.get("/live/search") {
            parameter("keyword", keyword)
        }.body()
    }

}