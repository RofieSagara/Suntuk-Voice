package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.user.UserBlockEntity
import kotlinx.coroutines.flow.Flow

interface UserBlockRepository {
   fun findOne(userID: String, targetID: String): Flow<UserBlockEntity?>
   suspend fun insert(data: UserBlockEntity)
   suspend fun insert(dataCollection: List<UserBlockEntity>)
}