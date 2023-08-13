package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
	ID           primitive.ObjectID  `json:"id,omitempty" bson:"_id,omitempty"`
	UserID       primitive.ObjectID  `json:"user_id" bson:"user_id"`
	ReactionID   primitive.ObjectID  `json:"reaction_id" bson:"reaction_id"`
	CurrencyType ReactionCurrentType `json:"currency_type" bson:"currency_type"`
	UserCoins    float64             `json:"user_coins" bson:"user_coins"`
	UserKarma    float64             `json:"user_karma" bson:"user_karma"`
	Price        float64             `json:"current_price" bson:"current_price"`
	Discount     float64             `json:"discount" bson:"discount"`
	Amount       float64             `json:"amount" bson:"amount"`
	Total        float64             `json:"total" bson:"total"`
	Log          *DataLog            `json:"log,omitempty" bson:"log,omitempty"`
 */
data class TransactionReactionResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    @SerializedName("reaction_id") val reactionID: String,
    @SerializedName("currency_type") val currencyType: String,
    @SerializedName("user_coins") val userCoins: Double,
    @SerializedName("user_karma") val userKarma: Double,
    @SerializedName("current_price") val price: Double,
    val discount: Double,
    val amount: Double,
    val total: Double,
    val log: DataLogResponse
)

/*
	ID      primitive.ObjectID   `json:"id,omitempty" bson:"_id,omitempty"`
	UserID  primitive.ObjectID   `json:"user_id" bson:"user_id"`
	Type    TransactionKarmaType `json:"type" bson:"type"`
	Current float64              `json:"current" bson:"current"`
	Amount  float64              `json:"amount" bson:"amount"`
	Log     *DataLog             `json:"log,omitempty" bson:"log,omitempty"`
 */
data class TransactionKarmaResponse(
    val id: String,
    @SerializedName("user_id") val userID: String,
    val type: String,
    val current: Double,
    val amount: Double,
    val log: DataLogResponse
)