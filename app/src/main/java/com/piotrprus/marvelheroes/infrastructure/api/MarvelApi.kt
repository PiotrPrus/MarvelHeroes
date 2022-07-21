package com.piotrprus.marvelheroes.infrastructure.api

import com.piotrprus.marvelheroes.infrastructure.dto.CharactersResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MarvelApi(private val httpClient: HttpClient) {

    suspend fun getHeroes(limit: Int = 20, offset: Int = 0): CharactersResponse =
        httpClient.get("v1/public/characters") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
}