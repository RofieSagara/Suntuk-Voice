package com.indev.suntuk.service.model

data class CommentLike(
    val id: String,
    val commentID: String,
    val userID: String,
    val available: Boolean,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun CommentLike.Companion.fromEntity(entity: com.indev.suntuk.service.local.entity.comment.CommentLikeEntity): CommentLike {
    return CommentLike(
        id = entity.id,
        commentID = entity.commentId,
        userID = entity.userId,
        available = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}