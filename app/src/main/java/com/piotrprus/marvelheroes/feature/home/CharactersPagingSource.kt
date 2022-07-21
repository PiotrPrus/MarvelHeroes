package com.piotrprus.marvelheroes.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.piotrprus.marvelheroes.infrastructure.repository.CharactersRepository
import com.piotrprus.marvelheroes.ui.model.CharacterItem

class CharactersSource(private val charactersRepository: CharactersRepository) :
    PagingSource<Int, CharacterItem>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> =
        try {
            val offset = params.key ?: 0
            val list = charactersRepository.fetchHeroes(offset)
            LoadResult.Page(
                data = list,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (list.isEmpty()) null else offset + params.loadSize
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
}