package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.stuk.StukLikeEntity

data class StukLike(
    val id: String = "",
    val stukId: String = "",
    val userId: String = "",
    val isAvailable: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun StukLike.Companion.fromEntity(entity: StukLikeEntity): StukLike {
    return StukLike(
        id = entity.id,
        stukId = entity.stukId,
        userId = entity.userId,
        isAvailable = entity.available,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
    )
}