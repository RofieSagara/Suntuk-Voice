package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
type PrivateMessage struct {
	ID      primitive.ObjectID   `json:"id,omitempty" bson:"_id,omitempty"`
	ChatID  string               `json:"chat_id" bson:"chat_id"`
	Users   []primitive.ObjectID `json:"users" bson:"users"`
	Sender  primitive.ObjectID   `json:"sender" bson:"sender"`
	Message *Message             `json:"message" bson:"message"`
	Status  PrivateMessageStatus `json:"status" bson:"status"`
	Log     *DataLog             `json:"log" bson:"log"`
}
 */
data class PrivateMessageResponse(
    val id: String,
    @SerializedName("chat_id") val chatID: String,
    val users: List<String>,
    val sender: String,
    val message: MessageResponse,
    val status: String,
    val log: DataLogResponse
)

/*
type GroupChat struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Title       string             `json:"title" bson:"title"`
	Description string             `json:"description" bson:"description"`
	Profile     string             `json:"profile" bson:"profile"`
	Thumb       string             `json:"thumb" bson:"thumb"`
	Member      int64              `json:"member" bson:"member"`
	IsPublic    bool               `json:"is_public" bson:"is_public"`
	LastMessage *Message           `json:"last_message" bson:"last_message"`
	IsAvailable bool               `json:"is_available" bson:"is_available"`
	Log         *DataLog           `json:"log" bson:"log"`
}
 */
data class GroupChatResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    val title: String,
    val description: String,
    val profile: String,
    val thumb: String,
    val member: Long,
    @SerializedName("is_public") val isPublic: Boolean,
    @SerializedName("last_message") val lastMessage: MessageResponse,
    @SerializedName("is_available") val isAvailable: Boolean,
    val log: DataLogResponse
)

/*
type GroupMessage struct {
	ID            primitive.ObjectID   `json:"id,omitempty" bson:"_id,omitempty"`
	GroupID       primitive.ObjectID   `json:"group_id" bson:"group_id"`
	UserID        primitive.ObjectID   `json:"user_id" bson:"user_id"`
	UserProfile   string               `json:"user_profile" bson:"user_profile"`
	UserNickname  string               `json:"user_nickname" bson:"user_nickname"`
	ParentMessage *ParentMessage       `json:"parent_message" bson:"parent_message"`
	Message       *Message             `json:"message" bson:"message"`
	Recipients    []primitive.ObjectID `json:"recipients" bson:"recipients"`
	IsPrivate     bool                 `json:"is_private" bson:"is_private"`
	Status        GroupMessageStatus   `json:"status" bson:"status"`
	Log           *DataLog             `json:"log" bson:"log"`
}
 */
data class GroupMessageResponse(
    val id: String,
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("user_profile") val userProfile: String,
    @SerializedName("user_nickname") val userNickname: String,
    @SerializedName("parent_message") val parentMessage: ParentMessageResponse,
    val message: MessageResponse,
    val recipients: List<String>,
    @SerializedName("is_private") val isPrivate: Boolean,
    val status: String,
    val log: DataLogResponse
)

/*
type GroupChatMember struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	GroupID     primitive.ObjectID `json:"group_id" bson:"group_id"`
	UserID      primitive.ObjectID `json:"user_id" bson:"user_id"`
	Profile     string             `json:"profile" bson:"profile"`
	Nickname    string             `json:"nickname" bson:"nickname"`
	IsAvailable bool               `json:"is_available" bson:"is_available"`
	IsBot       bool               `json:"is_bot" bson:"is_bot"`
	Log         *DataLog           `json:"log" bson:"log"`
}
 */
data class GroupChatMemberResponse(
    val id: String,
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    val profile: String,
    val nickname: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("is_bot") val isBot: Boolean,
    val log: DataLogResponse
)