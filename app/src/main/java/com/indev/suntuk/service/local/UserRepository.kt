package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.user.UserBlockEntity
import com.indev.suntuk.service.local.entity.user.UserCurrencyEntity
import com.indev.suntuk.service.local.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun liveUser(): Flow<UserEntity?>
    fun liveUserCurrency(): Flow<UserCurrencyEntity?>
    suspend fun insert(user: UserEntity)
    fun findOneByUID(userUID: String): Flow<UserEntity?>
    suspend fun clear()
}