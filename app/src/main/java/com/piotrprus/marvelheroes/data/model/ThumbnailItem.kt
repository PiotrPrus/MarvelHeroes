package com.piotrprus.marvelheroes.data.model

@kotlinx.serialization.Serializable
data class ThumbnailItem(
    val id: Int,
    val imageUrl: String,
    val detailUrl: String?
)
