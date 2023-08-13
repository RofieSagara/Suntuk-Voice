package com.indev.suntuk.service.model

data class StukUI(
    val stuk: Stuk,
    val stukLike: StukLike? = null,
    val parentStuk: Stuk? = null,
    val parentStukLike: StukLike? = null,
    val stukComment: Comment? = null,
    val stukCommentLike: CommentLike? = null,
)