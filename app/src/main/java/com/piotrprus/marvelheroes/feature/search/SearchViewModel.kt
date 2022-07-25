package com.piotrprus.marvelheroes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.infrastructure.repository.CharactersRepository
import com.piotrprus.marvelheroes.ui.common.UiMessage
import com.piotrprus.marvelheroes.ui.common.UiMessageManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val uiMessageManager = UiMessageManager()

    private val mutableState = MutableStateFlow(SearchViewState())
    val state: StateFlow<SearchViewState> = mutableState

    private val unfilteredList = MutableStateFlow<List<CharacterItem>>(emptyList())

    init {
        viewModelScope.launch {
            searchQuery.debounce(300)
                .onEach { query ->
                    if (query.isEmpty()) {
                        unfilteredList.value = emptyList()
                        mutableState.update { it.copy(list = listOf()) }
                    } else {
                        unfilteredList.value = charactersRepository.fetchHeroes(startWith = query)
                        val results = unfilteredList.value
                            .filterWithDescription(state.value.descriptionFilter)
                            .filterWithPhoto(state.value.photoFilter)
                        mutableState.update { it.copy(list = results) }
                    }
                }
                .catch { throwable -> uiMessageManager.emitMessage(UiMessage(throwable)) }
                .collect()
        }

        uiMessageManager.message
            .onEach { message ->
                mutableState.update { it.copy(uiMessage = message) }
            }
            .launchIn(viewModelScope)
        searchQuery.onEach { query ->
            mutableState.update { it.copy(searchQuery = query) }
        }.launchIn(viewModelScope)
    }

    fun search(query: String) {
        searchQuery.value = query
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

    fun descriptionFilterClick() {
        viewModelScope.launch {
            mutableState.update {
                it.copy(
                    descriptionFilter = state.value.descriptionFilter.not(),
                    list = unfilteredList.value.filterWithDescription(state.value.descriptionFilter.not())
                        .filterWithPhoto(state.value.photoFilter)
                )
            }
        }
    }

    fun photoFilterClick() {
        viewModelScope.launch {
            mutableState.update {
                it.copy(
                    photoFilter = state.value.photoFilter.not(),
                    list = unfilteredList.value.filterWithPhoto(state.value.photoFilter.not())
                        .filterWithDescription(state.value.descriptionFilter)
                )
            }
        }
    }

    private fun List<CharacterItem>.filterWithPhoto(filter: Boolean): List<CharacterItem> =
        if (filter) this.filterNot {
            it.imageUrl.contains("image_not_available")
        } else this

    private fun List<CharacterItem>.filterWithDescription(filter: Boolean): List<CharacterItem> =
        if (filter) this.filter { it.description.isNotEmpty() }
        else this
}