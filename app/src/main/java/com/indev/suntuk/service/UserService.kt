package com.indev.suntuk.service

import com.indev.suntuk.service.model.User
import com.indev.suntuk.service.model.UserBlock
import com.indev.suntuk.service.model.UserCurrency
import kotlinx.coroutines.flow.Flow

interface UserService {
    fun registerLogin(): Flow<Unit>
    fun liveUser(): Flow<User>
    fun liveUserCurrency(): Flow<UserCurrency>
    fun updateProfileNickname(profile: String, nickname: String, isCustomNickname: Boolean): Flow<Unit>
    fun syncUser(): Flow<Unit>
    fun blockUser(targetID: String): Flow<Unit>
    fun unblockUser(targetID: String): Flow<Unit>
    fun isUserBlocked(targetID: String): Flow<Boolean>
    fun syncBlockedUsers(): Flow<Unit>
    fun logout(): Flow<Unit>
}