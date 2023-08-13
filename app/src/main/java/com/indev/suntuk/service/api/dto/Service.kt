package com.indev.suntuk.service.api.dto

import com.google.gson.annotations.SerializedName
import com.indev.suntuk.service.api.response.*

/*
type GetUserDTO struct {
	ID primitive.ObjectID `json:"id"`
}
 */
data class GetUserDTO(
    val id: String
)

/*
type GetUserRes struct {
	User          *domains.User          `json:"user"`
	UserAuthorize *domains.UserAuthorize `json:"user_authorize"`
	UserCurrency  *domains.UserCurrency  `json:"user_currency"`
}
 */
data class GetUserRes(
    val user: UserResponse,
    @SerializedName("user_authorize") val userAuthorize: UserAuthorizeResponse,
    @SerializedName("user_currency") val userCurrency: UserCurrencyResponse
)

/*
type GetUIDRes struct {
	User          *domains.User          `json:"user"`
	UserAuthorize *domains.UserAuthorize `json:"user_authorize"`
	UserCurrency  *domains.UserCurrency  `json:"user_currency"`
}
 */
data class GetUIDRes(
    val user: UserResponse,
    @SerializedName("user_authorize") val userAuthorize: UserAuthorizeResponse,
    @SerializedName("user_currency") val userCurrency: UserCurrencyResponse
)

/*
type UserServiceFetchBlockUserResponse struct {
	BlockUsers []*domains.UserBlock `json:"block_users"`
	BlockedBy  []*domains.UserBlock `json:"blocked_by"`
}
 */
data class UserServiceFetchBlockUserResponse(
    @SerializedName("block_users") val blockUsers: List<UserBlockResponse>,
    @SerializedName("blocked_by") val blockedBy: List<UserBlockResponse>
)

/*
type StukServiceCreateTextResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateTextResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceCreateImageResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateImageResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceCreateVoiceResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateVoiceResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceQuoteResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceQuoteResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceCreateThreadTextResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateThreadTextResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceCreateThreadImageResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateThreadImageResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceCreateThreadVoiceResponse struct {
	StukID primitive.ObjectID `json:"stuk_id"`
}
 */
data class StukServiceCreateThreadVoiceResponse(
    @SerializedName("stuk_id") val stukID: String
)

/*
type StukServiceGetResponse struct {
	Stuk     *domains.Stuk     `json:"stuk"`
	StukLike *domains.StukLike `json:"stuk_like"`
}
 */
data class StukServiceGetResponse(
    val stuk: StukResponse,
    @SerializedName("stuk_like") val stukLike: StukLikeResponse
)

/*
type StukServiceFetchOwnerResponse struct {
	Stuk       []*domains.Stuk     `json:"stuk"`
	StukLikes  []*domains.StukLike `json:"stuk_likes"`
	IsLastPage bool                `json:"is_last_page"`
}
 */
data class StukServiceFetchOwnerResponse(
    val stuk: List<StukResponse>,
    @SerializedName("stuk_likes") val stukLikes: List<StukLikeResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean
)

/*
type StukServiceFetchTimelineResponse struct {
	Stuk       []*domains.Stuk     `json:"stuk"`
	StukLikes  []*domains.StukLike `json:"stuk_likes"`
	IsLastPage bool                `json:"is_last_page"`
}
 */
data class StukServiceFetchTimelineResponse(
    val stuk: List<StukResponse>,
    @SerializedName("stuk_likes") val stukLikes: List<StukLikeResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean
)

/*
type StukServiceSearchResponse struct {
	Stuk      []*domains.Stuk     `json:"stuk"`
	StukLikes []*domains.StukLike `json:"stuk_likes"`
}
 */
data class StukServiceSearchResponse(
    val stuk: List<StukResponse>,
    @SerializedName("stuk_likes") val stukLikes: List<StukLikeResponse>
)

/*
type CommentServiceCreateTextResponse struct {
	CommentID primitive.ObjectID `json:"comment_id"`
}
 */
data class CommentServiceCreateTextResponse(
    @SerializedName("comment_id") val commentID: String
)

/*
type CommentServiceGetResponse struct {
	Comment     *domains.Comment     `json:"comment"`
	CommentLike *domains.CommentLike `json:"comment_like"`
}
 */
data class CommentServiceGetResponse(
    val comment: CommentResponse,
    @SerializedName("comment_like") val commentLike: CommentLikeResponse
)

/*
type CommentServiceFetchResponse struct {
	Comments     []*domains.Comment     `json:"comments"`
	IsLastPage   bool                   `json:"is_last_page"`
	CommentLikes []*domains.CommentLike `json:"comment_likes"`
}
 */
data class CommentServiceFetchResponse(
    val comments: List<CommentResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean,
    @SerializedName("comment_likes") val commentLikes: List<CommentLikeResponse>
)

/*
type CommentServiceSearchResponse struct {
	Comments     []*domains.Comment     `json:"comments"`
	CommentLikes []*domains.CommentLike `json:"comment_likes"`
	IsLastPage   bool                   `json:"is_last_page"`
}
 */
data class CommentServiceSearchResponse(
    val comments: List<CommentResponse>,
    @SerializedName("comment_likes") val commentLikes: List<CommentLikeResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean
)

/*
type ReplyServiceCreateTextResponse struct {
	ReplyID primitive.ObjectID `json:"reply_id"`
}
 */
data class ReplyServiceCreateTextResponse(
    @SerializedName("reply_id") val replyID: String
)

/*
type ReplyServiceGetResponse struct {
	Reply     *domains.Reply     `json:"reply"`
	ReplyLike *domains.ReplyLike `json:"reply_like"`
}
 */
data class ReplyServiceGetResponse(
    val reply: ReplyResponse,
    @SerializedName("reply_like") val replyLike: ReplyLikeResponse
)

/*
type ReplyServiceFetchResponse struct {
	Replies    []*domains.Reply     `json:"replies"`
	ReplyLikes []*domains.ReplyLike `json:"reply_likes"`
	IsLastPage bool                 `json:"is_last_page"`
}
 */
data class ReplyServiceFetchResponse(
    val replies: List<ReplyResponse>,
    @SerializedName("reply_likes") val replyLikes: List<ReplyLikeResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean
)

/*
type ReplyServiceSearchResponse struct {
	Replies    []*domains.Reply     `json:"replies"`
	ReplyLikes []*domains.ReplyLike `json:"reply_likes"`
	IsLastPage bool                 `json:"is_last_page"`
}
 */
data class ReplyServiceSearchResponse(
    val replies: List<ReplyResponse>,
    @SerializedName("reply_likes") val replyLikes: List<ReplyLikeResponse>,
    @SerializedName("is_last_page") val isLastPage: Boolean
)

/*
type MessageServiceSendTextMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceSendTextMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceClaimDeliveryMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceClaimDeliveryMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceSendTextGroupMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendTextGroupMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceSendImageMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceSendImageMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceSendImageGroupMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendImageGroupMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceClaimReadMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceClaimReadMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceSendVoiceMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceSendVoiceMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceDeleteMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	ChatID    string             `json:"chat_id"`
}
 */
data class MessageServiceDeleteMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("chat_id") val chatID: String
)

/*
type MessageServiceDeleteGroupMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
}
 */
data class MessageServiceDeleteGroupMessageResponse(
    @SerializedName("message_id") val messageID: String
)

/*
type MessageServiceLeaveGroupResponse struct {
	GroupID primitive.ObjectID `json:"group_id"`
	UserID  primitive.ObjectID `json:"user_id"`
}
 */
data class MessageServiceLeaveGroupResponse(
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String
)

/*
type MessageServiceJoinGroupResponse struct {
	GroupID           primitive.ObjectID `json:"group_id"`
	UserID            primitive.ObjectID `json:"user_id"`
	GroupChatMemberID primitive.ObjectID `json:"group_chat_member_id"`
}
 */
data class MessageServiceJoinGroupResponse(
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("group_chat_member_id") val groupChatMemberID: String
)

/*
type MessageServiceInviteGroupResponse struct {
	GroupID           primitive.ObjectID `json:"group_id"`
	UserID            primitive.ObjectID `json:"user_id"`
	GroupChatMemberID primitive.ObjectID `json:"group_chat_member_id"`
}
 */
data class MessageServiceInviteGroupResponse(
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("group_chat_member_id") val groupChatMemberID: String
)

/*
type MessageServiceSendBotTextMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendBotTextMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceSendBotImageMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendBotImageMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceSendBotVoiceMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendBotVoiceMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceManipulateGroupMemberResponse struct {
	GroupID           primitive.ObjectID `json:"group_id"`
	UserID            primitive.ObjectID `json:"user_id"`
	GroupChatMemberID primitive.ObjectID `json:"group_chat_member_id"`
}
 */
data class MessageServiceManipulateGroupMemberResponse(
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("group_chat_member_id") val groupChatMemberID: String
)

/*
type MessageServiceSendVoiceGroupMessageResponse struct {
	MessageID primitive.ObjectID `json:"message_id"`
	GroupID   primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceSendVoiceGroupMessageResponse(
    @SerializedName("message_id") val messageID: String,
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceUpdateProfileNicknameGroupMemberResponse struct {
	GroupID           primitive.ObjectID `json:"group_id"`
	UserID            primitive.ObjectID `json:"user_id"`
	GroupChatMemberID primitive.ObjectID `json:"group_chat_member_id"`
}
 */
data class MessageServiceUpdateProfileNicknameGroupMemberResponse(
    @SerializedName("group_id") val groupID: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("group_chat_member_id") val groupChatMemberID: String
)

/*
type MessageServiceCreateGroupResponse struct {
	GroupID primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceCreateGroupResponse(
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceDeleteGroupResponse struct {
	GroupID primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceDeleteGroupResponse(
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceUpdateGroupResponse struct {
	GroupID primitive.ObjectID `json:"group_id"`
}
 */
data class MessageServiceUpdateGroupResponse(
    @SerializedName("group_id") val groupID: String
)

/*
type MessageServiceFetchGroupChatResponse struct {
	Groups       []*domains.GroupChat       `json:"groups"`
	GroupMembers []*domains.GroupChatMember `json:"group_members"`
}
 */
data class MessageServiceFetchGroupChatResponse(
    @SerializedName("groups") val groups: List<GroupChatResponse>,
    @SerializedName("group_members") val groupMembers: List<GroupChatMemberResponse>
)

/*
type LiveServiceCreateRoomResponse struct {
	Token    string             `json:"token"`
	UpStream string             `json:"up_stream"`
	UserID   primitive.ObjectID `json:"user_id"`
}
 */
data class LiveServiceCreateRoomResponse(
    @SerializedName("token") val token: String,
    @SerializedName("up_stream") val upStream: String,
    @SerializedName("user_id") val userID: String
)

/*
type LiveServiceGetResponse struct {
	Live     *domains.Live        `json:"live"`
	Counter  *domains.LiveCounter `json:"counter"`
	Presence int64                `json:"presence"`
}
 */
data class LiveServiceGetResponse(
    @SerializedName("live") val live: LiveResponse,
    @SerializedName("counter") val counter: LiveCounterResponse,
    @SerializedName("presence") val presence: Long
)

/*
type LiveServiceFetchPopularResponse struct {
	Live    []*LivePresenceUI      `json:"live"`
	Counter []*domains.LiveCounter `json:"counter"`
}
 */
data class LiveServiceFetchPopularResponse(
    @SerializedName("live") val live: List<LivePresenceUI>,
    @SerializedName("counter") val counter: List<LiveCounterResponse>
)

/*
type LiveServiceFetchCategoryResponse struct {
	Live    []*LivePresenceUI      `json:"live"`
	Counter []*domains.LiveCounter `json:"counter"`
}
 */
data class LiveServiceFetchCategoryResponse(
    @SerializedName("live") val live: List<LivePresenceUI>,
    @SerializedName("counter") val counter: List<LiveCounterResponse>
)

/*
type LiveServiceFetchLatestResponse struct {
	Live    []*LivePresenceUI      `json:"live"`
	Counter []*domains.LiveCounter `json:"counter"`
}
 */
data class LiveServiceFetchLatestResponse(
    @SerializedName("live") val live: List<LivePresenceUI>,
    @SerializedName("counter") val counter: List<LiveCounterResponse>
)

/*
type LiveServiceSearchResponse struct {
	Live    []*LivePresenceUI      `json:"live"`
	Counter []*domains.LiveCounter `json:"counter"`
}
 */
data class LiveServiceSearchResponse(
    @SerializedName("live") val live: List<LivePresenceUI>,
    @SerializedName("counter") val counter: List<LiveCounterResponse>
)

/*
type BotServiceGetBotResponse struct {
	Bot *domains.Bot `json:"bot"`
}
 */
data class BotServiceGetBotResponse(
    @SerializedName("bot") val bot: BotResponse
)

/*
type LivePresenceUI struct {
	Live     *domains.Live `json:"live"`
	Presence int64         `json:"presence"`
}
 */
data class LivePresenceUI(
    @SerializedName("live") val live: LiveResponse,
    @SerializedName("presence") val presence: Long
)