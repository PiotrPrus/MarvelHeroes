package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailDTO
)
