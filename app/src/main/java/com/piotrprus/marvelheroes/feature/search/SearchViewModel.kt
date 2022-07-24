package com.piotrprus.marvelheroes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        viewModelScope.launch {
            searchQuery.debounce(300)
                .onEach { query ->
                    if (query.isEmpty()) mutableState.update { it.copy(list = listOf()) }
                    else {
                        val results = charactersRepository.fetchHeroes(startWith = query)
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
}