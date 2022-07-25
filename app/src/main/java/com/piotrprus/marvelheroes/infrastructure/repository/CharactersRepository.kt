package com.piotrprus.marvelheroes.infrastructure.repository

import com.piotrprus.marvelheroes.infrastructure.api.MarvelApi
import com.piotrprus.marvelheroes.data.model.CharacterItem
import com.piotrprus.marvelheroes.data.model.ThumbnailItem

interface CharactersRepository {
    suspend fun fetchHeroes(offset: Int, limit: Int): List<CharacterItem>
    suspend fun getDetail(heroId: Int): Result<CharacterItem>
    suspend fun getComics(heroId: Int): Result<List<ThumbnailItem>>
    suspend fun getEvents(heroId: Int): Result<List<ThumbnailItem>>
    suspend fun fetchHeroes(startWith: String, limit: Int = 50): List<CharacterItem>

    class Impl(private val marvelApi: MarvelApi) : CharactersRepository {
        override suspend fun fetchHeroes(offset: Int, limit: Int): List<CharacterItem> {
            val response = marvelApi.getHeroes(offset = offset, limit = limit)
            return response.data.results.map {
                CharacterItem(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.thumbnail.url,
                    description = it.description
                )
            }
        }

        override suspend fun fetchHeroes(startWith: String, limit: Int): List<CharacterItem> {
            val response = marvelApi.getHeroes(startWith = startWith, limit = limit)
            return response.data.results.map {
                CharacterItem(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.thumbnail.url,
                    description = it.description
                )
            }
        }

        override suspend fun getDetail(heroId: Int): Result<CharacterItem> =
            kotlin.runCatching {
                val results = marvelApi.getHeroDetail(heroId).data.results
                if (results.isEmpty()) throw Exception("Cannot find hero with id: $heroId")
                results.first().let {
                    CharacterItem(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.thumbnail.url,
                        description = it.description
                    )
                }
            }

        override suspend fun getComics(heroId: Int): Result<List<ThumbnailItem>> =
            kotlin.runCatching {
                val results = marvelApi.getHeroComics(heroId).data.results
                results.mapNotNull { comics ->
                    comics.thumbnail?.let { ThumbnailItem(id = comics.id, imageUrl = it.url, detailUrl = comics.urls?.first()?.url) }
                }.filterNot { it.imageUrl.contains("image_not_available") }
            }

        override suspend fun getEvents(heroId: Int): Result<List<ThumbnailItem>> =
            kotlin.runCatching {
                val results = marvelApi.getHeroEvents(heroId).data.results
                results.mapNotNull { event ->
                    event.thumbnail?.let { ThumbnailItem(id = event.id, imageUrl = it.url, detailUrl = event.urls?.first()?.url) }
                }.filterNot { it.imageUrl.contains("image_not_available") }
            }
    }
}