package com.thusee.tandemlisting.data

import com.squareup.moshi.Json

data class CommunityResponse(
    @field:Json(name = "errorCode") val errorCode: Int? = 0,
    @field:Json(name = "type") val type: String = "",
    @field:Json(name = "response") val data: List<Data> = emptyList(),
)

data class Data(
    @field:Json(name = "topic") val topic: String = "",
    @field:Json(name = "firstName") val firstName: String = "",
    @field:Json(name = "pictureUrl") val pictureUrl: String = "",
    @field:Json(name = "natives") val natives: List<String> = emptyList(),
    @field:Json(name = "learns") val learns: List<String> = emptyList(),
    @field:Json(name = "referenceCnt") val referenceCnt: Int = 0,
)