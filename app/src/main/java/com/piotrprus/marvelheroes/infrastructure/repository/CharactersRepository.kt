package com.piotrprus.marvelheroes.infrastructure.repository

import com.piotrprus.marvelheroes.infrastructure.api.MarvelApi
import com.piotrprus.marvelheroes.ui.model.CharacterItem

interface CharactersRepository {
    suspend fun fetchHeroes(offset: Int): List<CharacterItem>

    class Impl(private val marvelApi: MarvelApi): CharactersRepository {
        override suspend fun fetchHeroes(offset: Int): List<CharacterItem> {
            val response = marvelApi.getHeroes(offset = offset)
            return response.data.results.map {
                CharacterItem(id = it.id, name = it.name, imageUrl = it.thumbnail.url)
            }
        }
    }
}