package com.indev.suntuk.service.api.response

/*
type Broker struct {
	ID     primitive.ObjectID `json:"id" bson:"_id"`
	Target primitive.ObjectID `json:"target" bson:"target"`
	Type   BrokerType         `json:"type" bson:"type"`
	Data   string             `json:"data" bson:"data"`
	Log    *DataLog           `json:"log" bson:"log"`
}
 */
data class BrokerResponse(
    val id: String,
    val target: String,
    val type: String,
    val data: String,
    val log: DataLogResponse
)