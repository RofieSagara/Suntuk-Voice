package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.user.UserAuthorizeEntity

interface UserAuthorizeRepository {
    suspend fun insert(data: UserAuthorizeEntity)
}