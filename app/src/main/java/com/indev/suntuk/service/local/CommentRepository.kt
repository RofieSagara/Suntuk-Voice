package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.comment.CommentEntity
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun liveLatestCommentByStukIDs(stukIDs: List<String>): Flow<List<CommentEntity>>
}