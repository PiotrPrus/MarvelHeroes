package com.piotrprus.marvelheroes.feature.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piotrprus.marvelheroes.infrastructure.database.FavouriteDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class FavouriteViewModel(private val store: FavouriteDataStore) : ViewModel() {
    val state = store.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}