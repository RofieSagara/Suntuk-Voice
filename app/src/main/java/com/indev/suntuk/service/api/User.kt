package com.indev.suntuk.service.api

import com.google.gson.JsonObject
import com.indev.suntuk.service.api.dto.GetUserRes
import com.indev.suntuk.service.api.dto.UserServiceFetchBlockUserResponse
import com.indev.suntuk.service.api.response.Res
import com.indev.suntuk.service.api.response.ResOK
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/*
type UserService interface {
	RegisterUserIfNotExist(ctx context.Context, params *RegisterUserIfNotExistDTO) error
	GetUser(ctx context.Context, params *GetUserDTO) (*GetUserRes, error)
	GetUID(ctx context.Context, params *GetUIDDTO) (*GetUIDRes, error)
	UpdateProfileNickname(ctx context.Context, params *UpdateProfileNicknameDTO) error
	TopUpCoins(ctx context.Context, params *TopUpCoinsDTO) error
	TopUpKarma(ctx context.Context, params *TopUpKarmaDTO) error
	BuyReaction(ctx context.Context, params *BuyReactionDTO) error
	InvokeAuthorize(ctx context.Context, params *InvokeAuthorizeDTO) error
	BlockUser(ctx context.Context, params *UserServiceBlockUserDTO) error
	FetchBlockUser(ctx context.Context, params *UserServiceFetchBlockUserDTO) (*UserServiceFetchBlockUserResponse, error)
}
 */
interface UserAPI {
    suspend fun registerUserIfNotExist(): Res<String>
    suspend fun getUser(): Res<GetUserRes>
    suspend fun updateProfileNickname(isCustomNickname: Boolean, nickname: String, profile: String): Res<String>
    suspend fun blockUser(targetID: String): Res<String>
    suspend fun unBlockUser(targetID: String): Res<String>
    suspend fun fetchBlockUser(): Res<UserServiceFetchBlockUserResponse>
}

class IUserAPI(private val client: HttpClient): UserAPI {
    override suspend fun registerUserIfNotExist(): Res<String> {
        return client.post("/user/register").body()
    }

    override suspend fun getUser(): Res<GetUserRes> {
       return client.get("/user").body()
    }

    override suspend fun updateProfileNickname(isCustomNickname: Boolean, nickname: String, profile: String): Res<String>{
        val bodyJson = JsonObject()
        bodyJson.addProperty("is_custom_nickname", isCustomNickname)
        bodyJson.addProperty("nickname", nickname)
        bodyJson.addProperty("profile", profile)

        return client.post("/user/update-profile-nickname") {
            contentType(ContentType.Application.Json)
            setBody(bodyJson)
        }.body()
    }

    override suspend fun blockUser(targetID: String): Res<String> {
        val bodyJson = JsonObject()
        bodyJson.addProperty("target_id", targetID)
        bodyJson.addProperty("value", true)

        return client.post("/user/block") {
            contentType(ContentType.Application.Json)
            setBody(bodyJson)
        }.body()
    }

    override suspend fun unBlockUser(targetID: String): Res<String> {
        val bodyJson = JsonObject()
        bodyJson.addProperty("target_id", targetID)
        bodyJson.addProperty("value", false)

        return client.put("/user/block") {
            contentType(ContentType.Application.Json)
            setBody(bodyJson)
        }.body()
    }

    override suspend fun fetchBlockUser(): Res<UserServiceFetchBlockUserResponse> {
       return client.get("/user/block").body()
    }
}