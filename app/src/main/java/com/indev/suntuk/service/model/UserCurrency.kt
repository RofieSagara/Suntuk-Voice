package com.indev.suntuk.service.model

import com.indev.suntuk.service.local.entity.user.UserCurrencyEntity

data class UserCurrency(
    val id: String = "",
    val coins: Double = 0.0,
    val karma: Double = 0.0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    companion object {}
}

fun UserCurrency.Companion.fromEntity(userCurrencyEntity: UserCurrencyEntity): UserCurrency {
    return UserCurrency(
        id = userCurrencyEntity.id,
        coins = userCurrencyEntity.coins,
        karma = userCurrencyEntity.karma,
        createdAt = userCurrencyEntity.createdAt,
        updatedAt = userCurrencyEntity.updatedAt,
    )
}
