package com.indev.suntuk.service

import com.indev.suntuk.service.model.CommentUI
import com.indev.suntuk.service.model.ReplyUI
import com.indev.suntuk.service.model.StukUI
import kotlinx.coroutines.flow.Flow

interface StukService {
    fun liveFetchTimeline(): Flow<List<StukUI>>
    fun liveFetchOwner(): Flow<List<StukUI>>
    fun liveFetchLike(): Flow<List<StukUI>>
    fun liveStuk(stukID: String): Flow<StukUI>

    fun loadStukLike(): Flow<Unit>
    fun loadMoreStukLike(): Flow<Unit>
    fun likeStuk(stukID: String): Flow<Unit>
    fun loadTimeline(): Flow<Unit>
    fun loadMoreTimeline(): Flow<Unit>

    // searchStuk its not save to local so we just request to backend and show it
    // that why we need manual pagination with lastID: String
    fun searchStuk(query: String, lastID: String): Flow<List<StukUI>>
    fun searchStuk(query: String): Flow<List<StukUI>>

    fun postStukText(text: String, isAnonymous: Boolean): Flow<Unit>
    fun postStukImage(text: String, image: List<String>, isAnonymous: Boolean): Flow<Unit>
    fun postStukVoice(text: String, voice: String, isAnonymous: Boolean): Flow<Unit>
    fun deleteStuk(stukID: String): Flow<Unit>

    fun postStukThread(stukID: String, text: String): Flow<Unit>
    fun postStukThreadImage(stukID: String, text: String, image: String): Flow<Unit>
    fun postStukThreadVoice(stukID: String, text: String, voice: String): Flow<Unit>
    fun deleteStukThread(stukID: String, threadID: String): Flow<Unit>

    fun quoteStuk(stukID: String, text: String, isAnonymous: Boolean): Flow<Unit>

    // Comment
    fun liveFetchComment(stukID: String): Flow<List<CommentUI>>
    fun liveComment(commentID: String): Flow<CommentUI>

    fun loadComment(stukID: String): Flow<Boolean>
    fun loadMoreComment(stukID: String): Flow<Boolean>

    fun likeComment(commentID: String): Flow<Unit>

    // searchStuk its not save to local so we just request to backend and show it
    // that why we need manual pagination with lastID: String
    fun searchComment(query: String, lastID: String): Flow<List<CommentUI>>
    fun searchComment(query: String): Flow<List<CommentUI>>

    fun postCommentText(stukID: String, text: String): Flow<Unit>
    fun deleteComment(commentID: String): Flow<Unit>

    //Reply
    fun loadReply(commentID: String): Flow<Boolean>
    fun loadMoreReply(commentID: String): Flow<Boolean>

    fun likeReply(replyID: String): Flow<Unit>

    fun searchReply(query: String, lastID: String): Flow<List<ReplyUI>>
    fun searchReply(query: String): Flow<List<ReplyUI>>

    fun postReplyText(commentID: String, text: String): Flow<Unit>
    fun deleteReply(replyID: String): Flow<Unit>
}