package com.piotrprus.marvelheroes.data.model

data class FavouriteItem(
    val info: CharacterItem,
    val comics: List<ThumbnailItem>,
    val events: List<ThumbnailItem>
)
