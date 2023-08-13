package com.indev.suntuk.service.model

data class CommentUI(
    val comment: Comment,
    val commentLike: CommentLike?,
    val reply: List<ReplyUI>
)