package com.indev.suntuk.service.api.response

import com.google.gson.annotations.SerializedName

/*
type Bot struct {
	ID          primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	AuthorID    primitive.ObjectID `json:"author_id" bson:"author_id"`
	Token       string             `json:"token" bson:"token"`
	Profile     string             `json:"profile" bson:"profile"`
	Name        string             `json:"name" bson:"name"`
	Hook        string             `json:"hook" bson:"hook"`
	Description string             `json:"description" bson:"description"`
	Available   bool               `json:"available" bson:"available"`
	Log         *DataLog           `json:"log,omitempty" bson:"log,omitempty"`
}
 */
data class BotResponse(
    val id: String,
    @SerializedName("author_id") val authorID: String,
    val token: String,
    val profile: String,
    val name: String,
    val hook: String,
    val description: String,
    val available: Boolean,
    val log: DataLogResponse
)