package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class EventsResponse(
    val code: Int,
    val status: String,
    val data: EventsResponseData
)

@kotlinx.serialization.Serializable
data class EventsResponseData(
    val results: List<EventDTO> = emptyList()
)

@kotlinx.serialization.Serializable
data class EventDTO(
    val id: Int,
    val title: String,
    val thumbnailDTO: ThumbnailDTO? = null
)
