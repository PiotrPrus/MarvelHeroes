package com.piotrprus.marvelheroes.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piotrprus.marvelheroes.infrastructure.repository.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val heroId: Int,
    private val repository: CharactersRepository
) : ViewModel() {

    private val mutableState: MutableStateFlow<HeroDetailViewState> = MutableStateFlow(
        HeroDetailViewState()
    )
    val state: StateFlow<HeroDetailViewState> get() = mutableState

    init {
        viewModelScope.launch {
            repository.getDetail(heroId)
                .onSuccess { result -> mutableState.update { it.copy(info = result) } }
                .onFailure {  }
        }
        viewModelScope.launch {
            repository.getComics(heroId)
                .onSuccess { comics -> mutableState.update { it.copy(comics = comics) } }
                .onFailure {  }
        }
        viewModelScope.launch {
            repository.getEvents(heroId)
                .onSuccess { events -> mutableState.update { it.copy(events = events) } }
        }
    }
}