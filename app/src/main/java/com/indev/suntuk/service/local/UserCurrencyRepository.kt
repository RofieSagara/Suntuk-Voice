package com.indev.suntuk.service.local

import com.indev.suntuk.service.local.entity.user.UserCurrencyEntity

interface UserCurrencyRepository {
    suspend fun insert(data: UserCurrencyEntity)
}