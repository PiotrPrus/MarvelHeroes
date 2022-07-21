package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class CharacterDTO(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailDTO
)

@kotlinx.serialization.Serializable
data class ThumbnailDTO(
    val path: String,
    val extension: String
)
