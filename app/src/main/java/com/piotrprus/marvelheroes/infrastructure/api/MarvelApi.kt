package com.piotrprus.marvelheroes.infrastructure.api

import com.piotrprus.marvelheroes.infrastructure.dto.CharactersResponse
import com.piotrprus.marvelheroes.infrastructure.dto.ComicsResponse
import com.piotrprus.marvelheroes.infrastructure.dto.EventsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MarvelApi(private val httpClient: HttpClient) {

    suspend fun getHeroes(limit: Int = 100, offset: Int = 0): CharactersResponse =
        httpClient.get("v1/public/characters") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getHeroes(startWith: String, limit: Int): CharactersResponse =
        httpClient.get("v1/public/characters") {
            parameter("limit", limit)
            parameter("nameStartsWith", startWith)
        }.body()

    suspend fun getHeroDetail(heroId: Int): CharactersResponse =
        httpClient.get("v1/public/characters/$heroId").body()

    suspend fun getHeroComics(heroId: Int): ComicsResponse =
        httpClient.get("v1/public/characters/$heroId/comics") {
            parameter("limit", 5)
            parameter("orderBy", "onsaleDate")
            parameter("format", "comic")
        }.body()

    suspend fun getHeroEvents(heroId: Int): EventsResponse =
        httpClient.get("v1/public/characters/$heroId/events") {
            parameter("limit", 5)
            parameter("orderBy", "startDate")
        }.body()
}