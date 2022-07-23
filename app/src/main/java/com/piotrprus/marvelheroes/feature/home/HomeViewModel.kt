package com.piotrprus.marvelheroes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piotrprus.marvelheroes.data.model.CharacterItem
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val pagingSource: CharactersSource
) : ViewModel() {

    val pagedList: Flow<PagingData<CharacterItem>> =
        Pager(PAGING_CONFIG) { pagingSource }.flow.cachedIn(viewModelScope)

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 50,
            initialLoadSize = 100
        )
    }

}