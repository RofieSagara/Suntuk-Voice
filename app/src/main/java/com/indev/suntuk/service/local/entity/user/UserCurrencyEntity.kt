package com.indev.suntuk.service.local.entity.user

import com.indev.suntuk.service.api.response.UserCurrencyResponse
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/*
type UserCurrency struct {
	ID    primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	Coins float64            `json:"coins" bson:"coins"`
	Karma float64            `json:"karma" bson:"karma"`
	Log   *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
class UserCurrencyEntity: RealmObject {
    @PrimaryKey var id: String = ""
    var coins: Double = 0.0
    var karma: Double = 0.0
    var createdAt: Long = 0
    var updatedAt: Long = 0

    companion object {}
}

fun UserCurrencyEntity.Companion.fromResponse(userCurrencyResponse: UserCurrencyResponse): UserCurrencyEntity {
    val userCurrencyEntity = UserCurrencyEntity()
    userCurrencyEntity.id = userCurrencyResponse.id
    userCurrencyEntity.coins = userCurrencyResponse.coins
    userCurrencyEntity.karma = userCurrencyResponse.karma
    userCurrencyEntity.createdAt = userCurrencyResponse.log.createdAt
    userCurrencyEntity.updatedAt = userCurrencyResponse.log.updatedAt
    return userCurrencyEntity
}