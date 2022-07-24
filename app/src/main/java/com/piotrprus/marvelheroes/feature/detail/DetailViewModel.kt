package com.piotrprus.marvelheroes.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piotrprus.marvelheroes.data.model.FavouriteItem
import com.piotrprus.marvelheroes.infrastructure.database.FavouriteDataStore
import com.piotrprus.marvelheroes.infrastructure.repository.CharactersRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val heroId: Int,
    private val repository: CharactersRepository,
    private val favouriteDataStore: FavouriteDataStore
) : ViewModel() {

    private val mutableState: MutableStateFlow<HeroDetailViewState> = MutableStateFlow(
        HeroDetailViewState()
    )
    val state: StateFlow<HeroDetailViewState> get() = mutableState

    init {
        viewModelScope.launch {
            repository.getDetail(heroId)
                .onSuccess { result -> mutableState.update { it.copy(info = result) } }
                .onFailure { }
        }
        viewModelScope.launch {
            repository.getComics(heroId)
                .onSuccess { comics -> mutableState.update { it.copy(comics = comics) } }
                .onFailure { }
        }
        viewModelScope.launch {
            repository.getEvents(heroId)
                .onSuccess { events -> mutableState.update { it.copy(events = events) } }
        }
        favouriteDataStore.isStored(heroId).onEach { isStored ->
            mutableState.update { it.copy(isFavourite = isStored) }
        }.launchIn(viewModelScope)
    }

    fun onFavouriteClick() {
        if (state.value.isFavourite) {
            deleteFavourite()
        } else {
            addToFavourite()
        }
    }

    private fun deleteFavourite() {
        viewModelScope.launch {
            favouriteDataStore.deleteItem(heroId)
        }
    }

    private fun addToFavourite() {
        viewModelScope.launch {
            state.value.info?.let { info ->
                favouriteDataStore.addItem(
                    FavouriteItem(
                        info = info,
                        comics = state.value.comics ?: emptyList(),
                        events = state.value.events ?: emptyList()
                    )
                )
            }
        }
    }
}