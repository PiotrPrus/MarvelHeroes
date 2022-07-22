package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class ComicsResponse(
    val code: Int,
    val status: String,
    val data: ComicsResponseData
)

@kotlinx.serialization.Serializable
data class ComicsResponseData(
    val results: List<ComicsDTO> = emptyList()
)

@kotlinx.serialization.Serializable
data class ComicsDTO(
    val id: Int,
    val title: String,
    val thumbnailDTO: ThumbnailDTO? = null
)
