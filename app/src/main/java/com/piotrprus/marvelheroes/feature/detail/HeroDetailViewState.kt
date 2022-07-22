package com.piotrprus.marvelheroes.feature.detail

import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.data.model.ThumbnailItem

data class HeroDetailViewState(
    val info: CharacterItem? = null,
    val comics: List<ThumbnailItem> = emptyList(),
    val events: List<ThumbnailItem> = emptyList(),
    val isFavourite: Boolean = false
)
