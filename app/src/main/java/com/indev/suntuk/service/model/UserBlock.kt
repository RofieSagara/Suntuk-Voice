package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.user.UserEntity

data class UserBlock(
    val id: String = "",
    val uid: String = "",
    val email: String = "",
    val profile: String = "",
    val nickname: String = "",
    val accessType: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun UserBlock.Companion.fromEntity(userEntity: UserEntity): UserBlock {
    return UserBlock(
        id = userEntity.id,
        uid = userEntity.uid,
        email = userEntity.email,
        profile = userEntity.profile,
        nickname = userEntity.nickname,
        accessType = userEntity.accessType,
        createdAt = userEntity.createdAt,
        updatedAt = userEntity.updatedAt,
    )
}
