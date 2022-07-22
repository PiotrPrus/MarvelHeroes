package com.piotrprus.marvelheroes.infrastructure.api

import com.piotrprus.marvelheroes.TestUtils
import com.piotrprus.marvelheroes.infrastructure.di.createJson
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

fun createMarvelApiMockClient(): HttpClient {
    val responseHeaders =
        headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

    return HttpClient(MockEngine) {
        install(ContentNegotiation) { json(createJson()) }
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/v1/public/characters" -> respond(
                        TestUtils.readFile("20characters.json"),
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                    "/v1/public/characters/23062/comics" -> respond(
                        TestUtils.readFile("iron_man_comics.json"),
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                    "/v1/public/characters/23062/events" -> respond(
                        TestUtils.readFile("iron_man_events.json"),
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                    else -> error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
    }
}