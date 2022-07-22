package com.piotrprus.marvelheroes.infrastructure.dto

@kotlinx.serialization.Serializable
data class ThumbnailDTO(
    val path: String,
    val extension: String
) {
    val url = "$path.$extension".replace("http", "https")
}