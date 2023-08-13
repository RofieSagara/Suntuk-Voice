package com.indev.suntuk.service.local.entity.user

import com.indev.suntuk.service.api.response.UserResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var uid: String = ""
    var email: String = ""
    var profile: String = ""
    var nickname: String = ""
    var accessType: String = ""
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun UserEntity.Companion.fromResponse(userResponse: UserResponse): UserEntity {
    val userEntity = UserEntity()
    userEntity.id = userResponse.id
    userEntity.uid = userResponse.uid
    userEntity.email = userResponse.email
    userEntity.profile = userResponse.profile
    userEntity.nickname = userResponse.nickname
    userEntity.accessType = userResponse.accessType
    userEntity.createdAt = userResponse.log.createdAt
    userEntity.updatedAt = userResponse.log.updatedAt
    return userEntity
}