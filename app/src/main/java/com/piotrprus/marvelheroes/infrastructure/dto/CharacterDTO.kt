package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailDTO
)

@kotlinx.serialization.Serializable
data class ThumbnailDTO(
    val path: String,
    val extension: String
) {
    val url = "$path.$extension".replace("http", "https")
}
