package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: String,
    val uid: String,
    val email: String,
    val profile: String,
    val nickname: String,
    @SerializedName("access") val accessType: String,
    val log: DataLogResponse
)

data class UserAuthorizeResponse(
    val id: String,
    @SerializedName("create_timeline") val timeline: Long,
    @SerializedName("create_comment") val comment: Long,
    @SerializedName("create_reply") val reply: Long,
    @SerializedName("create_message") val message: Long,
    @SerializedName("create_live") val live: Long,
    val log: DataLogResponse
)

data class UserCurrencyResponse(
    val id: String,
    val coins: Double,
    val karma: Double,
    val log: DataLogResponse
)

data class UserReactionResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("reaction_id") val reactionID: String,
    val key: String,
    @SerializedName("thumb_key") val thumbKey: String,
    val amount: Int,
    val log: DataLogResponse
)

data class UserBlockResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("target_id") val targetID: String,
    val log: DataLogResponse
)