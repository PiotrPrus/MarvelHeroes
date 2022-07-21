@file:OptIn(ExperimentalCoroutinesApi::class)

package com.piotrprus.marvelheroes.infrastructure.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MarvelApiTest {

    private val marvelApi = MarvelApi(createMarvelApiMockClient())

    @Test
    fun `Fetch 20 marvel heroes characters`() = runTest {
        val result = marvelApi.getHeroes(20, 0).data.results
        assertEquals(20, result.size)
    }
}