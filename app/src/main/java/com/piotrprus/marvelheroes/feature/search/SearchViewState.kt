package com.piotrprus.marvelheroes.feature.search

import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.ui.common.UiMessage

data class SearchViewState(
    val list: List<CharacterItem> = emptyList(),
    val uiMessage: UiMessage? = null,
    val searchQuery: String = ""
)
