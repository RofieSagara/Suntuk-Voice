package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.comment.CommentLikeEntity
import kotlinx.coroutines.flow.Flow

interface CommentLikeRepository {
    fun liveFetchUserLikeByCommentIDs(commentIdList: List<String>): Flow<List<CommentLikeEntity>>
}